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
import org.json.JSONArray;

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
    * @param batch   The original {@link Batch}.
    * @param browser The {@link BrowserWindow}. */
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
        String statex;
        // Check Progress
        while (true) {
          try {
            if (batch.id() > 0) {
              resultString = senderRep().serverRep().livy().checkBatchProgress(batch.id(), 10, 1);
              if (resultString != null) {
                result = new JSONObject(resultString);
                statex = result.getString("state");
                senderRep().setState(statex);
                log.debug("State = " + statex);
                if (statex.equals("success") || statex.equals("dead")) {
                  break;
                  }
                }
              }
            Thread.sleep(1000); // 1s
            }
          catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            }          
          }
        JSONArray logArray = result.getJSONArray("log");
        String fullLog = "";
        for (Object logEntry : logArray) {
          fullLog += logEntry.toString() + "\n";
          }
        String state = result.getString("state");
        Text text = new Text();
        if (state.equals("success")) {
          text.setFill(Color.DARKBLUE);
          }
        else if (state.equals("dead")) {
          text.setFill(Color.DARKRED);
          }
        else {
          log.error("Unknown state " + state);
          }
        log.info(state + " : " + fullLog);
        // Get Log
        resultString = senderRep().serverRep().livy().getBatchLog(batch.id(), 10, 1);
        result = new JSONObject(resultString);
        logArray = result.getJSONArray("log");
        fullLog += "\n\n";
        for (Object logEntry : logArray) {
          fullLog += logEntry.toString() + "\n";
          }
        log.debug(fullLog);
        final String fullLog1 = fullLog; // so it can go to inner fcion
        // to synchronise threads
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            text.setText("state = " + state + "\n\nlog = " + fullLog1.replaceAll("\\\\n", "") + "\n\n");
            senderRep().setResult(text); // check, if the selected tab is correct
            }
          });
        }
      };
    thread.start();
    }
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem show = new MenuItem("Show Sender",  Images.icon(Images.SENDER));
    show.setOnAction(new BatchEventHandler(this));
    menuItems.add(show);
    return menuItems;
    }  
     
  /** Give the Batch id.
    * @return The Batch id. */
  public int id() {
    return batch().id();
    }
    
  /** Give the keeping {@link SenderRep}.
    * @return The keeping {@link SenderRep}. */
  public SenderRep senderRep() {
    return SenderRep.create(sender(), browser());
    }
        
  /** Give the associated hosting {@link Sender}.
    * @return The associated hosting {@link Sender}. */
  public Sender sender() {
    return batch().sender();
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
