package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;

// org.json
import org.json.JSONObject;
import org.json.JSONArray;

// Log4J
import org.apache.log4j.Logger;

/** <code>Job</code> represents running <em>Spark</em> job
  * transfer.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Job extends Element {
  
  /** Create new Job.
    * Check the progress.
    * @param name    The Job name.
    * @param session The hosting {@link Session}.
    * @param id      The statement id.
    * @param browser The {@link BrowserWindow}. */
  // TBD: show progress
  // TBD: change font to BOLD when done
  // TBD: open tab with result when done
  public Job(String        name,
             Session       session,
             int           id,
             BrowserWindow browser) {
    super(name, browser);
    Thread thread = new Thread(){
      @Override
      public void run(){
        String resultString;
        JSONObject result;
        JSONObject output;
        JSONObject data;
        String status;
        double progress;
        while (true) {
          try {
            resultString = session.server().livy().checkProgress(session.id(), id);
            result = new JSONObject(resultString);
            progress = result.getDouble("progress");
            log.info("Progress = " + progress);
            if (progress == 1.0) {
              output = result.getJSONObject("output");
              status = output.getString("status");
              data = output.getJSONObject("data");
              log.info(status + " : " + data);
              break;
              }
            Thread.sleep(10000);
            }
          catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            }          
          }
        }
      };
    thread.start();
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Job.class);

  }
