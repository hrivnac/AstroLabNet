package com.astrolabsoftware.AstroLabNet.HBaser;

import com.astrolabsoftware.AstroLabNet.Utils.SmallHttpClient;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// org.json
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

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
    
  /** Get results.
    * <pre>
    * GET /-table-/scanner/-scannerId-
    * </pre>
    * @param table     The requested table name.
    * @param scannerId The assigned <em>scanner</em> id.
    * @return          The command result. */
  public String getResults(String table,
                           String scannerId) {
    JSONObject result = new JSONObject(getResultsEncoded(table, scannerId));
    JSONArray rows = result.getJSONArray("Row");
    JSONArray cells;
    String answer = "";
    for (int i = 0; i < rows.length(); i++) {
      answer += decode(rows.getJSONObject(i).getString("key")) + ":\n";
      cells = rows.getJSONObject(i).getJSONArray("Cell");
      for (int j = 0; j < cells.length(); j++) {
         answer += "\t" + decode(cells.getJSONObject(j).getString("column"))
                + " = " + decode(cells.getJSONObject(j).getString("$")) + "\n";
        }
      }
    return answer;
    }
    
  /** Scan table.
    * @param table The requested table name.
    * @return      The command result. */
  public String scan(String table) {
    String scannerId = initScanner(table);
    return getResults(table, scannerId);
    }
   
  /** Encode {@link String} to REST server string.
    * @param s The string.
    * @return The encodeed REST server string. */
  private String encode(String s) {
    return Base64.getEncoder().encodeToString(s.getBytes());
    }
    
  /** Decode REST server string.
    * @param s The encoded REST server string.
    * @return The decode  REST server string. */
  private String decode(String s) {
    return new String(Base64.getDecoder().decode(s));
    }
    
  @Override
  public String toString() {
    return "HBaseClient of " + super.toString();
    }

  /** Logging . */
  private static Logger log = Logger.getLogger(HBaseClient.class);

  }
