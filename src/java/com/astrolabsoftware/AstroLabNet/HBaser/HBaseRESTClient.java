package com.astrolabsoftware.AstroLabNet.HBaser;

import com.astrolabsoftware.AstroLabNet.Utils.SmallHttpClient;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// org.json
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

// Java
import java.util.Map;
import java.util.HashMap;

// Log4J
import org.apache.log4j.Logger;

/** <code>HBaseRESTCLient</code> is the bridge to the <em>HBase</em> REST service:
  * <a href="https://www.cloudera.com/documentation/enterprise/5-9-x/topics/admin_hbase_rest_api.html">API</a>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class HBaseRESTClient {
  
  /** Connect to the server.
    * @param url The url of the server. */
  public HBaseRESTClient(String url) {
    log.info("Connecting to HBase Server " + url);
    _url = url;
    }
    
  /** Get server status.
    * <pre>
    * GET /version/cluster
    * GET /status/cluster
    * GET /
    * </pre>
    * @return The full server status. */
  public String getStatus() {
    String resultString = "";
    try {
      resultString = SmallHttpClient.get(_url + "/version/cluster", null) + "\n"
                   + SmallHttpClient.get(_url + "/status/cluster",  null) + "\n"
                   + SmallHttpClient.get(_url + "/",                null);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      AstroLabNetException.reportException("Request has failed", e, log);
      }
    log.debug("Result:\n" + resultString.trim());
    return resultString;
    }
    
  /** Initiate <em>scanner</em> on the server.
    * <pre>
    * PUT /-table-/scanner
    * </pre>
    * @param table The requested table name.
    * @return The assigned <em>scanner</em> id. */
  // TBD: parametrise batch size
  public String initScanner(String table) {
    log.info("Creating Scanner for " + table);
    String resultString = "";
    JSONObject result = null;
    try {
      resultString = SmallHttpClient.putXML(_url + "/" + table + "/scanner", "<Scanner batch='100'/>", null, "Location");
      }
    catch (AstroLabNetException e) {
      log.info(e);
      AstroLabNetException.reportException("Request has failed", e, log);
      return "";
      }
    log.debug("Result:\n" + resultString.trim());
    return resultString.substring(resultString.lastIndexOf("/") + 1);
    }
    
  /** Get results.
    * <pre>
    * GET /-table-/scanner/-scannerId-
    * </pre>
    * @param table     The requested table name.
    * @param scannerId The assigned <em>scanner</em> id.
    * @return             The command result, in <em>xml</em>. */
  public String getResults(String table,
                           String scannerId) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Accept", "application/json");
    headers.put("Content-Type", "application/json");
    String resultString = "";
    JSONObject result = null;
    try {
      resultString = SmallHttpClient.get(_url + "/" + table + "/scanner/" + scannerId, headers);
      result = new JSONObject(resultString);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      AstroLabNetException.reportException("Request has failed", e, log);
      }
    log.debug("Result:\n" + result.toString(2));
    return result.toString(2);
    }
    
  /** Scan table.
    * @param table The requested table name.
    * @return      The command result, in <em>xml</em>. */
  public String scan(String table) {
    String scannerId = initScanner(table);
    return getResults(table, scannerId);
    }
    
  /** Initiate <em>scanner</em> on the server.
    * <pre>
    * PUT /-table-/fakerow
    * </pre>
    * @param table   The requested table name.
    * @param key     The row key (byte-encoded).
    * @param entries The {@link Map} of columns in the form <code>family:column -&gt; value</code> (byte-encoded). */
  public void putEncoded(String              table,
                         String              key,
                         Map<String, String> entries) {
    String rowsXML = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><CellSet><Row key='" + key + "'>";
    for (Map.Entry<String, String> entry : entries.entrySet()) { 
      rowsXML += "<Cell column='" + entry.getKey() + "'>" + entry.getValue() + "</Cell>";
      }
    rowsXML += "</Row></CellSet>";
    try {
      SmallHttpClient.putXML(_url + "/" + table + "/fakerow", rowsXML, null, null);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      AstroLabNetException.reportException("Request has failed", e, log);
      return;
      }
    }

  @Override
  public String toString() {
    return "HBaseRESTClient(" + _url + ")";
    }
    
  private String _url;

  /** Logging . */
  private static Logger log = Logger.getLogger(HBaseRESTClient.class);

  }
