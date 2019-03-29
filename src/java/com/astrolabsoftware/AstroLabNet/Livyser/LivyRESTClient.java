package com.astrolabsoftware.AstroLabNet.Livyser;

import com.astrolabsoftware.AstroLabNet.Utils.SmallHttpClient;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// JavaFX
import javafx.util.Pair;

// org.json
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

// Java
import java.util.List;
import java.util.ArrayList;

// Log4J
import org.apache.log4j.Logger;

/** <code>LivyRESTCLient</code> is the bridge to the <em>Livy</em> REST service:
  * <a href="https://livy.incubator.apache.org/docs/latest/rest-api.html">API</a>.
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
    * <pre>
    * POST /sessions {"kind":*language*}
    * </pre>
    * @param language The {@link Language} of the {@link Session}.
    * @param tries     How many times to try.
    * @param sleep     How many <tt>s</tt> wait between tries.
    * @return The new session number. */
  // TBD: allow closing session
  public int initSession(Language language,
                         int      tries,
                         int      sleep) {
    log.info("Creating Session in " + language);
    boolean success = false;
    int i = 0;
    String result = "";
    while (!success && i++ <= tries) {
      try {
        Thread.sleep(1000 * sleep);
        result = SmallHttpClient.postJSON(_url + "/sessions", "{\"kind\":\"" + language.asSpark() + "\"}", null, null);
        success = true;
        }
      catch (AstroLabNetException e) {
        log.debug("Request has failed", e);
        }
      catch (InterruptedException e) {
        break;
        }
      }
    if (success) {
      log.debug("Result:\n" + result.trim());
      return new JSONObject(result).getInt("id");
      }
    else {
      return -1;
      }
    }
    
  /** Initiate batch on the server.
    * <pre>
    * POST /batches {"file":*file*, "className":*classname*}
    * </pre>
    * @param file      The jar filename.
    * @param className The <em>main</em> className.
    * @param tries     How many times to try.
    * @param sleep     How many <tt>s</tt> wait between tries.
    * @return The new batch number. */
  // TBD: allow closing session
  public int initBatch(String file,
                       String className,
                       int    tries,
                       int    sleep) {
    log.info("Creating Batch " + className + " from " + file);
    boolean success = false;
    int i = 0;
    String result = "";
    while (!success && i++ <= tries) {
      try {
        Thread.sleep(1000 * sleep);
        result = SmallHttpClient.postJSON(_url + "/batches", "{\"file\":\"" + file + "\", \"className\":\"" + className + "\"}", null, null);
        success = true;
        }
      catch (AstroLabNetException e) {
        log.debug("Request has failed", e);
        }
      catch (InterruptedException e) {
        break;
        }
      }
    if (success) {
      log.debug("Result:\n" + result.trim());
      return new JSONObject(result).getInt("id");
      }
    else {
      return -1;
      }
    }
    
  /** Get list of opened sessions.
    * <pre>
    * GET /sessions
    * </pre>
    * @return The {@link List} of {@link Pair}s of open session numbers and languages. */
  public List<Pair<Integer, Language>> getSessions() {
    List<Pair<Integer, Language>> ss = new ArrayList<>();
    String result = "";
    try {
      result = SmallHttpClient.get(_url + "/batches", null);
      }
    catch (AstroLabNetException e) {
      AstroLabNetException.reportException("Request has failed", e, log);
      return ss;
      }
    try {
      JSONArray sessions = new JSONObject(result).getJSONArray("sessions");
      for (int i = 0; i < sessions.length(); i++) {
        ss.add(new Pair<Integer, Language>(sessions.getJSONObject(i).getInt("id"),
                                           Language.fromSpark(sessions.getJSONObject(i).getString("kind"))));
        //getStatements(sessions.getJSONObject(i).getInt("id"));
        }
      }
    catch (JSONException e) {
      AstroLabNetException.reportException("Request has failed", e, log);
      return ss;
      }
    return ss;
    }
    
  /** Get list of opened batches.
    * <pre>
    * GET /batches
    * </pre>
    * @return The {@link List} of open batch session numbers. */
  public List<Integer> getBatches() {
    List<Integer> ss = new ArrayList<>();
    String result = "";
    try {
      result = SmallHttpClient.get(_url + "/batches", null);
      }
    catch (AstroLabNetException e) {
      AstroLabNetException.reportException("Request has failed", e, log);
      return ss;
      }
    try {
      JSONArray sessions = new JSONObject(result).getJSONArray("sessions");
      for (int i = 0; i < sessions.length(); i++) {
        ss.add(sessions.getJSONObject(i).getInt("id"));
        }
      }
    catch (JSONException e) {
      AstroLabNetException.reportException("Request has failed", e, log);
      return ss;
      }
    return ss;
    }
   
  /** Get list of opened statements.
    * <pre>
    * GET /sessions/-idSession-/statements
    * </re>
    * @param  idSession The existing session number.
    * @return           The {@link List} of {@link Integer}s of open statement numbers for a session. */
  public List<Integer> getStatements(int idSession) {
    List<Integer> ss = new ArrayList<>();
    String result = "";
    try {
      result = SmallHttpClient.get(_url + "/sessions/" + idSession + "/statements", null);
      }
    catch (AstroLabNetException e) {
      AstroLabNetException.reportException("Request has failed", e, log);
      return ss;
      }
    JSONArray statements = new JSONObject(result).getJSONArray("statements");
    for (int i = 0; i < statements.length(); i++) {
      ss.add(new Integer(statements.getJSONObject(i).getInt("id")));
      }
    return ss;
    }
     
  /** Send command to the server.
    * <pre>
    * POST /sessions/-idSession-/statements {"code":-code-}
    * </pre>
    * @param  idSession The existing session number.
    * @param  code      The <em>scala</code> to be run on the server.
    * @param  tries     How many times to try.
    * @param  sleep     How many <tt>s</tt> wait between tries.
    * @return           The new statement id. */
  public int sendCommand(int    idSession,
                         String code,
                         int    tries,
                         int    sleep) {
    log.info("Sending command '" + code + "'");
    code = code.trim()
               .replaceAll("\n", "\\\\n")
               .replaceAll("\"", "\\\\\"");
    String result = "";
    boolean success = false;
    int i = 0;
    while (!success && i++ <= tries) {
      try {
        Thread.sleep(1000 * sleep);
        result = SmallHttpClient.postJSON(_url + "/sessions/" + idSession + "/statements", "{\"code\":\"" + code + "\"}", null, null);
        success = true;
        }
      catch (AstroLabNetException e) {
        log.debug("Request has failed", e);
        }
      catch (InterruptedException e) {
        break;
        }
      }
    if (success) {
      log.debug("Result:\n" + result.trim());
      return new JSONObject(result).getInt("id");
      }
    else {
      return -1;
      }
    }
    
  /** Check command progreess, get results.
    * <pre>
    * GET /sessions/-idSession-/statements/-idStatement-
    * </pre>
    * @param  idSession   The existing session number.
    * @param  idStatement The statement number.
    * @param  tries       How many times to try.
    * @param  sleep       How many <tt>s</tt> wait between tries.
    * @return             The result. */
  public String checkSessionProgress(int idSession,
                                     int idStatement,
                                     int tries,
                                     int sleep) {
    String result = "";
    boolean success = false;
    int i = 0;
    while (!success && i++ <= tries) {
      try {
        Thread.sleep(1000 * sleep);
        result = SmallHttpClient.get(_url + "/sessions/" + idSession + "/statements/" + idStatement, null);
        success = true;
        }
      catch (AstroLabNetException e) {
        log.debug("Request has failed", e);
        }
      catch (InterruptedException e) {
        break;
        }
      }
    if (success) {
      log.debug("Result:\n" + result.trim());
      return result;
      }
    else {
      return null;
      }
    }
    
  /** Check batch progreess, get results.
    * <pre>
    * GET /batches/-idBatch-
    * </pre>
    * @param  idBatch     The existing batch session number.
    * @param  tries       How many times to try.
    * @param  sleep       How many <tt>s</tt> wait between tries.
    * @return             The result. */
  public String checkBatchProgress(int idBatch,
                                   int tries,
                                   int sleep) {
    String result = "";
    boolean success = false;
    int i = 0;
    while (!success && i++ <= tries) {
      try {
        Thread.sleep(1000 * sleep);
        result = SmallHttpClient.get(_url + "/batches/" + idBatch, null);
        success = true;
        }
      catch (AstroLabNetException e) {
        log.debug("Request has failed", e);
        }
      catch (InterruptedException e) {
        break;
        }
      }
    if (success) {
      log.debug("Result:\n" + result.trim());
      return result;
      }
    else {
      return null;
      }
    }
    
  /** Wait for the job to deliver result.
    * @param  idSession   The existing session number.
    * @param  idStatement The statement number.
    * @param  tries       How many times to try.
    * @param  sleep       How many <tt>s</tt> wait between requests.
    * @return             The result as <em>Json</em> string. */
  public String waitForActionResult(int idSession,
                                    int idStatement,
                                    int tries,
                                    int sleep) {
    log.info("Waiting for Action result");
    String resultString = null;  
    JSONObject result;
    double progress;
    while (true) {
      try {
        resultString = checkSessionProgress(idSession, idStatement, tries, sleep);
        result = new JSONObject(resultString);
        progress = result.getDouble("progress");
        log.debug("Progress = " + progress);
        if (progress == 1.0) {
          break;
          }
        Thread.sleep(1000 + sleep);
        }
      catch (InterruptedException e) {
        break;
        }          
      }
    return resultString;
    }
     
  /** Wait for the job to deliver result.
    * @param  idBatch     The existing batch session number.
    * @param  tries       How many times to try.
    * @param  sleep       How many <tt>s</tt> wait between requests.
    * @return             The result as <em>Json</em> string. */
  public String waitForJobResult(int idBatch,
                                 int tries,
                                 int sleep) {
    log.info("Waiting for Job result");
    String resultString = null;  
    JSONObject result;
    String state;
    while (true) {
      try {
        resultString = checkBatchProgress(idBatch, tries, sleep);
        result = new JSONObject(resultString);
        state = result.getString("state");
        log.debug("State = " + state);
        if (state.equals("success")) { // TBD: handle failure
          break;
          }
        Thread.sleep(1000 + sleep);
        }
      catch (InterruptedException e) {
        break;
        }          
      }
    return resultString;
    }
  
  /** Execute action, try until succeeds, wait for result.
    * @param cmd      The command to send.
    * @param language The command {@link Language}.
    * @return         The result as <em>Json</em> string. */
  public String executeAction(String   cmd,
                               Language language) {
    log.info("Executing command '" + cmd + "' in " + language + " and waiting for result");
    int sessionId   = initSession(language,       Integer.MAX_VALUE, 1);
    int statementId = sendCommand(sessionId, cmd, Integer.MAX_VALUE, 1);
    return waitForActionResult(sessionId, statementId,  Integer.MAX_VALUE, 1);
    }
    
  /** Send job, try until succeeds, wait for result.
    * @param file      The jar filename.
    * @param className The <em>main</em> className.
    * @return          The result as <em>Json</em> string. */
  public String sendJob(String file,
                        String className) {
    log.info("Sending '" + className + "' in " + file + " and waiting for result");
    int batchId = initBatch(file, className,    Integer.MAX_VALUE, 1);
    return waitForJobResult(batchId, Integer.MAX_VALUE, 1);
    }
    
  @Override
  public String toString() {
    return "LivyRESTClient(" + _url + ")";
    }
    
  private String _url;

  /** Logging . */
  private static Logger log = Logger.getLogger(LivyRESTClient.class);

  }
