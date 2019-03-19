package com.astrolabsoftware.AstroLabNet.HBaser;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Utils.SmallHttpClient;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// JavaFX
import javafx.util.Pair;

// org.json
import org.json.JSONObject;
import org.json.JSONArray;

// Java
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

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
    
  private String _url;

  /** Logging . */
  private static Logger log = Logger.getLogger(HBaseRESTClient.class);

  }
