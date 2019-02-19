package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

// org.json
import org.json.JSONObject;
import org.json.JSONArray;

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
    * @param browser    The {@link BrowserWindow}.
    * @param elements   The mother {@link TreeItem}.
    *                   TaskRep will be added to it, if not yet present.*/
  // TBD: do factory for other elements too
  public static TaskRep create(Task                 task,
                               BrowserWindow        browser,
                               TreeItem<ElementRep> elements) {
    TaskRep taskRep = _taskReps.get(task.toString());
    if (taskRep == null) {
      taskRep = new TaskRep(task, browser);
      log.info("Adding Task " + taskRep);
      _taskReps.put(task.toString(), taskRep);
      elements.getChildren().add(taskRep.item());
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
            resultString = sessionRep().serverRep().livy().checkProgress(sessionRep().id(), task.id());
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
  
  /** Give hosting {@link SessionRep}.
    * @return The hosting {@link SessionRep}. */
  public SessionRep sessionRep() {
    return SessionRep.create(task().session(), browser());
    }
    
  /** TBD */
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
