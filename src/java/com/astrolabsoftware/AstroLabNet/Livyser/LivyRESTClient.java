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

/** <code>LivyRIESTCLient</code> is the bridge to the <em>Livy</em> REST service.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
// TBD: handle full answer, check for errors
public class LivyRESTClient {
  
  /** Connect to the server.
    * @param url The url of the server. */
  public LivyRESTClient(String url) {
    log.info("Connecting to Livy Server " + url);
    _url = url;
    }
  
  /** Initiate session on the server.
    * @param language The {@link Language} of the {@link Session}.
    * @return The new session number. */
  public int initSession(Language language) {
    log.info("Creating Session in " + language);
    String result = "";
    try {
      result = SmallHttpClient.post(_url + "/sessions", "{\"kind\":\"" + language.asSpark() + "\"}", null);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      BrowserWindow.reportException("Request has failed", e, log);
      return 0;
      }
    log.debug("Result:\n" + result.trim());
    return new JSONObject(result).getInt("id");
    }
    
  /** Get list of opened sessions.
    * @return The {@link List} of {@link Pair}s of open session numbers and languages. */
  public List<Pair<Integer, Language>> getSessions() {
    List<Pair<Integer, Language>> ss = new ArrayList<>();
    String result = "";
    try {
      result = SmallHttpClient.get(_url + "/sessions", null);
      }
    catch (AstroLabNetException e) {
      BrowserWindow.reportException("Request has failed", e, log);
      return ss;
      }
    JSONArray sessions = new JSONObject(result).getJSONArray("sessions");
    for (int i = 0; i < sessions.length(); i++) {
      ss.add(new Pair<Integer, Language>(sessions.getJSONObject(i).getInt("id"),
                                         Language.fromSpark(sessions.getJSONObject(i).getString("kind"))));
      getStatements(sessions.getJSONObject(i).getInt("id"));
      }
    return ss;
    }
    
  /** Get list of opened statements.
    * @param  idSession The existing session number.
    * @return           The {@link List} of {@link Integer}s of open statement numbers for a session. */
  public List<Integer> getStatements(int idSession) {
    List<Integer> ss = new ArrayList<>();
    String result = "";
    try {
      result = SmallHttpClient.get(_url + "/sessions/" + idSession + "/statements", null);
      }
    catch (AstroLabNetException e) {
      BrowserWindow.reportException("Request has failed", e, log);
      return ss;
      }
    JSONArray statements = new JSONObject(result).getJSONArray("statements");
    for (int i = 0; i < statements.length(); i++) {
      ss.add(new Integer(statements.getJSONObject(i).getInt("id")));
      }
    return ss;
    }
  
  /** Send command to the server.
    * @param  idSession The existing session number.
    * @param  code      The <em>scala</code> to be run on the server.
    * @return           The command result, in <em>json</em>. */
  public String sendCommand(int    idSession,
                            String code) {
    String result = "";
    code = code.trim()
               .replaceAll("\n", "\\\\n")
               .replaceAll("\"", "\\\\\"");
    try {
      result = SmallHttpClient.post(_url + "/sessions/" + idSession + "/statements", "{\"code\":\"" + code + "\"}", null);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      BrowserWindow.reportException("Request has failed", e, log);
      }
    log.debug("Result:\n" + result.trim());
    return result;
    }
    
  /** Send command to the server.
    * @param  idSession   The existing session number.
    * @param  idStatement The statement number.
    * @return             The command result, in <em>json</em>. */
  public String checkProgress(int idSession,
                              int idStatement) {
    String result = "";
    try {
      result = SmallHttpClient.get(_url + "/sessions/" + idSession + "/statements/" + idStatement, null);
      }
    catch (AstroLabNetException e) {
      log.info(e);
      BrowserWindow.reportException("Request has failed", e, log);
      }
    log.debug("Result:\n" + result.trim());
    return result;
    }
    
  private String _url;

  /** Logging . */
  private static Logger log = Logger.getLogger(LivyRESTClient.class);

  }
