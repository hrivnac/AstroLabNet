package com.astrolabsoftware.AstroLabNet.Livyser;

import com.astrolabsoftware.AstroLabNet.Browser.RootFrame;
import com.astrolabsoftware.AstroLabNet.Utils.SmallHttpClient;

// Java
import java.util.Map;
import java.util.HashMap;

// Log4J
import org.apache.log4j.Logger;

public class LivyRI {
  
  public String sendCommand(String code) {
    Map<String, String> params = new HashMap<>();
    params.put("kind", "spark");
    //params.put("code", "1+1");
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type","application/json");
    String result = "xxx";
    try {
      result = SmallHttpClient.post("http://localhost:8998/sessions", params, headers);
      }
    catch (Exception e) {
      log.info(e);
      RootFrame.reportException("zzz", e, log);
    }
    return result;
    }

    /** Logging . */
  private static Logger log = Logger.getLogger(LivyRI.class);

  }
