package com.astrolabsoftware.AstroLabNet.HBaser;

import com.astrolabsoftware.AstroLabNet.Utils.SmallHttpClient;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

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
    String result = "";
    try {
      result = SmallHttpClient.get(_url + "/version/cluster", null) + "\n"
             + SmallHttpClient.get(_url + "/status/cluster",  null) + "\n"
             + SmallHttpClient.get(_url + "/",                null);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      AstroLabNetException.reportException("Request has failed", e, log);
      }
    log.debug("Result:\n" + result.trim());
    return result;
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
    String result = "";
    try {
      result = SmallHttpClient.postXML(_url + "/" + table + "/scanner", "<Scanner batch='100'/>", null, "Location");
      }
    catch (AstroLabNetException e) {
      log.info(e);
      AstroLabNetException.reportException("Request has failed", e, log);
      return "";
      }
    log.debug("Result:\n" + result.trim());
    return result.substring(result.lastIndexOf("/") + 1);
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
    String result = "";
    try {
      result = SmallHttpClient.get(_url + "/" + table + "/scanner/" + scannerId, headers);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      AstroLabNetException.reportException("Request has failed", e, log);
      }
    log.debug("Result:\n" + result.trim());
    return result;
    }
    
  private String _url;

  /** Logging . */
  private static Logger log = Logger.getLogger(HBaseRESTClient.class);

  }
