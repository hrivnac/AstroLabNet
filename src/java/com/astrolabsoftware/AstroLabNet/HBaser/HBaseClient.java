package com.astrolabsoftware.AstroLabNet.HBaser;

// Java
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Base64;

// Log4J
import org.apache.log4j.Logger;

/** <code>HBaseCLient</code> is the bridge to the <em>HBase</em> REST service
  * talking directly to TBD
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class HBaseClient extends HBaseRESTClient {
  
  /** Connect to the server.
    * @param url The url of the server. */
  public HBaseClient(String url) {
    super(url);
    }
    
  /** Initiate <em>scanner</em> on the server.
    * <pre>
    * PUT /-table-/fakerow
    * </pre>
    * @param table   The requested table name.
    * @param key     The row key.
    * @param columns The columns as <tt>family:column</tt>.
    * @param values  The columns values. <tt>null</tt> value will be ignored. */
  public void put(String   table,
                  String   key,
                  String[] columns,
                  String[] values) {
    Map<String, String> entries = new HashMap<>();
    List<String> valuesEnc  = new ArrayList<>();
    for (int i = 0; i < values.length; i++) {
      if (values[i] != null) {
        entries.put(encode(columns[i]), encode(values[i]));
        }
      }
    putEncoded(table, encode(key), entries);
    }
    
  /** Encode {@link String} to REST server string.
    * @param s The string.
    * @return The encodeed REST server string. */
  private String encode(String s) {
    return Base64.getEncoder().encodeToString(s.getBytes());
    }

  @Override
  public String toString() {
    return "HBaseClient of " + super.toString();
    }

  /** Logging . */
  private static Logger log = Logger.getLogger(HBaseClient.class);

  }
