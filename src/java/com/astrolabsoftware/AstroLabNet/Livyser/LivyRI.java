package com.astrolabsoftware.AstroLabNet.Livyser;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
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
    * @param url The url of the server.
    * @return    The new session number. */
  public int initSession(String url) {
    String result = "";
    try {
      result = SmallHttpClient.post(url + "/sessions", "{\"kind\":\"spark\"}", null);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      BrowserWindow.reportException("Request has failed", e, log);
      return 0;
      }
    return new JSONObject(result).getInt("id");
    }
    
  /** Get list of opened sessions.
    * @param  url The url of the server.
    * @return     The list of open session numbers. */
  public Integer[] getSessions(String url) {
    String result = "";
    try {
      result = SmallHttpClient.get(url + "/sessions", null);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      BrowserWindow.reportException("Request has failed", e, log);
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
    * @param  url  The url of the server.
    * @param  id   The existing sessin number.
    * @param  code The <em>scala</code> to be run on the server.
    * @return      The command result, in <em>json</em>. */
  public String sendCommand(String url,
                            int id,
                            String code) {
    Map<String, String> params = new HashMap<>();
    Map<String, String> headers = new HashMap<>();
    String result = "";
    try {
      result = SmallHttpClient.post("http://localhost:8998/sessions", "{\"kind\":\"spark\"}", headers);
      //result = SmallHttpClient.get("http://localhost:8998/sessions?kind=spark", headers);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      BrowserWindow.reportException("Request has failed", e, log);
    }
    return result;
    }

    /** Logging . */
  private static Logger log = Logger.getLogger(LivyRI.class);

  }
