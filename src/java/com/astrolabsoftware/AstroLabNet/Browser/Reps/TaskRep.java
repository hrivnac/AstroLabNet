package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Journal.Record;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;

// org.json
import org.json.JSONObject;

// Java
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// Log4J
import org.apache.log4j.Logger;

/** <code>TaskRep</code> is {@link BrowserWindow} representation of {@link Task}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class TaskRep extends ElementRep {
  
  /** Create new TaskRep as a <em>Singleton</em>.
    * @param task       The original {@link Task}.
    * @param browser    The {@link BrowserWindow}. */
  // TBD: do factory for other elements too
  public static TaskRep create(Task           task,
                               BrowserWindow  browser) {
    TaskRep taskRep = _taskReps.get(task.toString());
    if (taskRep == null) {
      taskRep = new TaskRep(task, browser);
      log.info("Adding Task " + taskRep);
      _taskReps.put(task.toString(), taskRep);
      }
    return taskRep;
    }
  
  private static Map<String, TaskRep> _taskReps = new HashMap<>();  
    
  /** Create new TaskRep.
    * Check the progress.
    * @param name       The represented {@link Task}.
    * @param browser    The {@link BrowserWindow}. */
  public TaskRep(Task          task,
                 BrowserWindow browser) {
    super(task, browser, Images.TASK);
    Thread thread = new Thread() {
      // check periodically status, untill progress = 1.0
      // then leave the thread and report results
      @Override
      public void run() {
        String resultString;
        JSONObject result;
        double progress;
        while (true) {
          try {
            resultString = sessionRep().serverRep().livy().checkSessionProgress(sessionRep().id(), task.id(), 10, 1);
            result = new JSONObject(resultString);
            progress = result.getDouble("progress");
            sessionRep().setProgress(progress);
            log.debug("Progress = " + progress);
            if (progress == 1.0) {
              break;
              }
            Thread.sleep(1000); // 1s
            }
          catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            }          
          }
        JSONObject output = result.getJSONObject("output");
        String status = output.getString("status");
        JSONObject data = null;
        Text text = new Text();
        if (status.equals("ok")) {
          data = output.getJSONObject("data");
          text.setFill(Color.DARKBLUE);
          }
        else if (status.equals("error")) {
          data = output;
          text.setFill(Color.DARKRED);
          }
        else {
          log.error("Unknown status " + status);
          }
        JSONObject d = data;
        log.info(status + " : " + d);
        new Record(session().server()).record("Action", "execute", "0", "0", null, null, output.toString(2), "testing"); // TBD: fill all fields
        // to synchronise threads
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            text.setText("status = " + status + "\n\noutput = " + output.toString(2).replaceAll("\\\\n", "") + "\n\n");
            sessionRep().setResult(text); // check, if the selected tab is correct
            }
          });
        }
      };
    thread.start();
    }
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem show = new MenuItem("Show Session",  Images.icon(Images.SESSION));
    show.setOnAction(new TaskEventHandler(this));
    menuItems.add(show);
    return menuItems;
    }

  /** Give the Statement id.
    * @return The Statement id. */
  public int id() {
    return task().id();
    }
  
  /** Give hosting {@link SessionRep}.
    * @return The hosting {@link SessionRep}. */
  public SessionRep sessionRep() {
    return SessionRep.create(session(), browser());
    }
    
  /** Give the associated hosting {@link Session}.
    * @return The associsted hosting {@link Session}. */
  public Session session() {
    return task().session();
    }
    
  /** Give the referenced {@link Task}.
    * @return The referenced {@link Task}. */
  public Task task() {
    return (Task)element();
    }
    
  @Override
  public String toString() {
    return task().toString();
    }
    
  /** Logging . */
  private static Logger log = Logger.getLogger(TaskRep.class);

  }
