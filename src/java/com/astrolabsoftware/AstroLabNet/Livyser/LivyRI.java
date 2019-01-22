package com.astrolabsoftware.AstroLabNet.Livyser;

import com.astrolabsoftware.AstroLabNet.Browser.RootFrame;
import com.astrolabsoftware.AstroLabNet.Utils.SmallHttpClient;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

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

/** <code>LivyRI</code> is the bridge to the <em>Livy</em> service.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class LivyRI {
  
  /** Initiate session on the server.
    * TBD */
  public int initSession(String url) {
    Map<String, String> params = new HashMap<>();
    params.put("kind", "spark");
    String result = "";
    try {
      result = SmallHttpClient.post(url + "/sessions", "{\"kind\":\"spark\"}", null);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      RootFrame.reportException("Request has failed", e, log);
      return 0;
      }
    return new JSONObject(result).getInt("id");
    }
  /** TBD */
  public Integer[] getSessions(String url) {
    String result = "";
    try {
      result = SmallHttpClient.get(url + "/sessions", null);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      RootFrame.reportException("Request has failed", e, log);
      return new Integer[0];
      }
    JSONArray sessions = new JSONObject(result).getJSONArray("sessions");
    List<Integer> ids = new ArrayList<>();
    for (int i = 0; i < sessions.length(); i++) {
      ids.add(sessions.getJSONObject(i).getInt("id"));
      }
    return ids.toArray(new Integer[0]);
    }
  
  /** Send command to the server.
    * TBD */
  public String sendCommand(String url,
                            int sessionId,
                            String code) {
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
