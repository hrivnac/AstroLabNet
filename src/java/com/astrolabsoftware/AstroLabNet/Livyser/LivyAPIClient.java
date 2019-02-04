package com.astrolabsoftware.AstroLabNet.Livyser;

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

/** <code>LivyAPICLient</code> is the bridge to the <em>Livy</em> API service.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
// TBD: handle full answer, check for errors
public class LivyAPIClient {
  
  /** Connect to the server.
    * @param url The url of the server. */
  public LivyAPIClient(String url) {
    log.info("Connecting to Livy Server " + url);
    _url = url;
    }
    
  private String _url;

  /** Logging . */
  private static Logger log = Logger.getLogger(LivyAPIClient.class);

  }
