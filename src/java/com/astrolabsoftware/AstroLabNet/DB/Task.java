package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

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

// Log4J
import org.apache.log4j.Logger;

/** <code>Task</code> represents running <em>Spark</em> task.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Task extends Element {
  
  /** Create new Task.
    * Check the progress.
    * @param name    The Task name.
    * @param session The hosting {@link Session}.
    * @param id      The statement id.
    * @param browser The {@link BrowserWindow}. */
  public Task(String        name,
              Session       session,
              int           id,
              BrowserWindow browser) {
    super(name, browser, Images.TASK);
    _session = session;
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
            resultString = session.server().livy().checkProgress(session.id(), id);
            result = new JSONObject(resultString);
            progress = result.getDouble("progress");
            log.info("Progress = " + progress);
            if (progress == 1.0) {
              break;
              }
            Thread.sleep(5000); // 5s
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
            session.setResult(text);
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
  
  /** Give hosting {@link Session}.
    * @return The hosting {@link Session}. */
  public Session session() {
    return _session;
    }
    
  private Session _session;  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(Task.class);

  }
