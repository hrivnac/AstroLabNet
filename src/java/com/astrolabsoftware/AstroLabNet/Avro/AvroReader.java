package com.astrolabsoftware.AstroLabNet.Avro;

import com.astrolabsoftware.AstroLabNet.Utils.Init;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;
import com.astrolabsoftware.AstroLabNet.DB.Server;

// Avro
import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.avro.io.DatumReader;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericData.Array;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.specific.SpecificDatumReader;

// Java
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Base64;

// ZTF
import ztf.alert.candidate;

// Log4J
import org.apache.log4j.Logger;

/** <code>AvroReader</code> reads <em>Avro</em> files.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class AvroReader {
    
  /** SelfTest.
    * @param args[0] The avro data filename.
    * @param args[1] The avro file filename (optional).
    * @throws IOException If file cannot be read. */
  public static void main(String[] args) throws IOException {
    Init.init(args);
    if (args.length == 0 || args.length > 2) {
      log.error("AvroReader <avro data file> [<avro schema file>]");
      System.exit(-1);
      }
    String schemaFN = null;
    if (args.length == 2) {
      schemaFN = args[1];
      }
    AvroReader ar = new AvroReader(null, schemaFN);
    ar.process(args[0]);
    }
    
  /** Create.
    * @param server   The {@link Server} to use for <em>Catalog</em>.
    *                 If <tt>null</tt>, the default {@link Server} will be tried.
    * @param schemaFN The filename of the schema file.
    *                 If <tt>null</tt>, schema will be read from data file.*/
  public AvroReader(Server server,
                    String schemaFN) {
    log.info("Using server " + server);
    log.info("Using schema from " + schemaFN);
    if (server == null) {
      _server = new Server("Local Host", null, null, "http://localhost:8080");
      }
    else {
      _server = server;
      }
    if (schemaFN == null) {
      _schema = null;
      }
    else {
      try {
        _schema = new Schema.Parser().parse(new File(schemaFN));
        }
      catch (IOException e) {
        log.warn("Cannot read schema " + schemaFN + ", will use schema from Avro file");
        }
      }
    }
        
  /** Process <em>Avro</em> alert file.
     * @param dataFN   The filename of the data file.
     * @thows IOException If problem with file reading. */
  // TBD: use generated schema (problem: it mixes ztf.alert namespace and class
  public void process(String dataFN) throws IOException {
    log.info("Loading " + dataFN);
    File file = new File(dataFN);
    /*
    DatumReader<candidate> candidateDatumReader = new SpecificDatumReader<candidate>(candidate.class);
    DataFileReader<candidate> dataFileReader = new DataFileReader<candidate>(file, candidateDatumReader);
    candidate c = null;
    while (dataFileReader.hasNext()) {
      c = dataFileReader.next(c);
      log.info(c);
      }*/
    DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>();
    DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, datumReader);
    GenericRecord record = null;
    while (dataFileReader.hasNext()) {
      record = dataFileReader.next(record);
      processAlert(record);
      }
    } 
  
  /** Process <em>Avro</em> alert.
    * @param record The full alert {@link GenericRecord}. */
  private void processAlert(GenericRecord record) {
    getSimpleFields(record, new String[]{"candid"});
    String key = record.get("objectId").toString(); 
    toCatalog(key, "d", "type", "alert"); 
    register(record, key, "r", new String[]{"candid"});
    register(record, key, "d", getSimpleFields(record, new String[]{"candid"}));
    processCandidate((GenericRecord)(record.get("candidate"))); // TBD: check cast
    Array prv_candidates = (Array)(record.get("prv_candidates")); // TBD: check cast
    int n = 0;
    for (Object o : prv_candidates) {
      toCatalog(key, "r" , "prv_candid_" + ++n, key + "_" + n);
      processPrvCandidate((GenericRecord)o, key + "_" + n); // TBD: check cast
      }
    processCutoutScience((GenericRecord)(record.get("cutoutScience")), key);
    }
  
  /** Process <em>Avro</em> candidate.
    * @param record The {@link GenericRecord} with <em>candidate</em>. */
  private void processCandidate(GenericRecord record) {
    String key = record.get("candid").toString();
    toCatalog(key, "d", "type", "candidate");
    register(record, key, "d", getSimpleFields(record, new String[]{}));
    }
    
  /** Process <em>Avro</em> prv_candidate.
    * @param record The {@link GenericRecord} with <em>prv_candidate</em>.
    * @param key0   The <em>HBase</em> key to use in absence of <em>Avro</em> key. */
  private void processPrvCandidate(GenericRecord record,
                                   String        key0) {
    String key = key0;
    if (record.get("candid") != null) {
      key = record.get("candid").toString();
      }
    toCatalog(key, "d", "type", "prv_candidate");
    register(record, key, "d", getSimpleFields(record, new String[]{}));
    }
    
  /** Process <em>Avro</em> cutoutScience.
    * @param record The {@link GenericRecord} with <em>cutoutScience</em>.
    * @param key0   The <em>HBase</em> key to use in absence of <em>Avro</em> key. */
  private void processCutoutScience(GenericRecord record,
                                    String        key) {
    toCatalog(key, "d", "type", "cutoutScience");
    register(record, key, "d", getSimpleFields(record, new String[]{}));
    register(record, key, "d", new String[]{"stampData"});
    }

  /** Register part of {@link GenericRecord} in <em>HBase</em>.
    * @param record  The {@link GenericRecord} to be registered in <em>HBase</em>.
    * @param key     The <em>HBase</em> key to be used.
    * @param family  The <em>HBase</em> family to be used.
    * @param columns The fields to be mapped to <em>HBase</em> columns. */
  private void register(GenericRecord record,
                        String        key,
                        String        family,
                        String[]      columns) {
    for (String s : columns) {
      Object o = record.get(s);
      if (o instanceof java.nio.ByteBuffer) {
        //o = new String(((java.nio.ByteBuffer)o).array()); // TBD
        }
      toCatalog(key, family, s, o.toString());
      }
    }
    
  /** Send row to <em>HBase</em>.
    * @param key    The <em>HBase</em> key to be used.
    * @param family The <em>HBase</em> family to be used.
    * @param column The <em>HBase</em> columns to be written to.
    * @param value  The value to be written. */
  // TBD: enable number of versions and timeout
  private void toCatalog(String key,
                         String family,
                         String column,
                         String value) {
    //log.info(key + " => " + family + ":" + column + " = " + value);
    _server.hbase().put("astrolabnet.catalog.1",
                        encode(key),
                        new String[]{encode(family + ":" + column)},
                        new String[]{encode(value)});
    }
 
  /** Get {@link Field}s corresponding to simple types
    * and having non-<code>null</code> values.
    * @param record The {@link GenericRecord} to use.
    * @param avoids The array of fields names not to report.
    * @return       The array of coressponding fields. */
  private String[] getSimpleFields(GenericRecord record,
                                   String[]      avoids) {
    List<String> fields = new ArrayList<>();
    Type type;
    String name;
    boolean veto;
    for (Field field : record.getSchema().getFields()) {
      type = field.schema().getType();
      name = field.name();
      if (type == Type.BOOLEAN ||
          type == Type.DOUBLE  ||
          type == Type.FLOAT   ||
          type == Type.LONG    ||
          type == Type.INT     ||
          type == Type.STRING) {
        veto = false;
        for (String avoid : avoids) {
          if (name.equals(avoid) || record.get(name) == null) {
            veto = true;
            }
          }
        if (!veto) {
          fields.add(name);
          }
        }
      }
    return fields.toArray(new String[]{});
    }
    
  /** Encode REST server string.
    * @param s The string.
    * @return The encodeed REST server string. */
  private String encode(String s) {
    return new String(Base64.getEncoder().encode(s.getBytes()));
    }
    
  private Server _server;  
    
  private Schema _schema;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(AvroReader.class);
                                                
  }
