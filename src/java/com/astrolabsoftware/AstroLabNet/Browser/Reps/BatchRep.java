package com.astrolabsoftware.AstroLabNet.Browser.Reps;

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

/** <code>BatchRep</code> is {@link BrowserWindow} representation of {@link Batch}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class BatchRep extends ElementRep {
  
  /** Create new BatchRep as a <em>Singleton</em>.
    * @param batch      The original {@link Batch}.
    * @param browser    The {@link BrowserWindow}. */
  // TBD: do factory for other elements too
  public static BatchRep create(Batch          batch,
                                BrowserWindow  browser) {
    BatchRep batchRep = _batchReps.get(batch.toString());
    if (batchRep == null) {
      batchRep = new BatchRep(batch, browser);
      log.info("Adding Batch " + batchRep);
      _batchReps.put(batch.toString(), batchRep);
      }
    return batchRep;
    }
  
  private static Map<String, BatchRep> _batchReps = new HashMap<>();  
    
  /** Create new BatchRep.
    * Check the progress.
    * @param name       The represented {@link Batch}.
    * @param browser    The {@link BrowserWindow}. */
  public BatchRep(Batch         batch,
                  BrowserWindow browser) {
    super(batch, browser, Images.BATCH);
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
            resultString = sessionRep().serverRep().livy().checkProgress(sessionRep().id(), batch.id());
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
    show.setOnAction(new BatchEventHandler(this));
    menuItems.add(show);
    return menuItems;
    }
  
  /** Give hosting {@link SessionRep}.
    * @return The hosting {@link SessionRep}. */
  public SessionRep sessionRep() {
    return SessionRep.create(session(), browser());
    }
    
  /** Give the associated hosting {@link Session}.
    * @return The associsted hosting {@link Session}. */
  public Session session() {
    return batch().session();
    }

  /** Give the associated hosting {@link Session} id.
    * @return The associated hosting {@link Session} id. */
  public int id() {
    return batch().id();
    }
    
  /** Give the referenced {@link Batch}.
    * @return The referenced {@link Batch}. */
  public Batch batch() {
    return (Batch)element();
    }
    
  @Override
  public String toString() {
    return batch().toString();
    }
    
  /** Logging . */
  private static Logger log = Logger.getLogger(BatchRep.class);

  }
