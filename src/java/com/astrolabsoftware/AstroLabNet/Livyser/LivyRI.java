package com.astrolabsoftware.AstroLabNet.Livyser;

import com.astrolabsoftware.AstroLabNet.Browser.RootFrame;
import com.astrolabsoftware.AstroLabNet.Utils.SmallHttpClient;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// Java
import java.util.Map;
import java.util.HashMap;

// Log4J
import org.apache.log4j.Logger;

/** <code>LivyRI</code> is the bridge to the <em>Livy</em> service.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class LivyRI {
  
  /** Send command to the server.
    * TBD */
  public String sendCommand(String code) {
    Map<String, String> params = new HashMap<>();
    params.put("kind", "spark");
    //params.put("code", "1+1");
    Map<String, String> headers = new HashMap<>();
    String result = "";
    try {
      result = SmallHttpClient.post("http://localhost:8998/sessions", "{\"kind\":\"spark\"}", headers);
      //result = SmallHttpClient.get("http://localhost:8998/sessions?kind=spark", headers);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      RootFrame.reportException("Request has failed", e, log);
    }
    return result;
    }

    /** Logging . */
  private static Logger log = Logger.getLogger(LivyRI.class);

  }
