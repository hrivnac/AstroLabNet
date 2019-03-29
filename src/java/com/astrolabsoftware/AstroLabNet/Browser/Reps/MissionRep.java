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

/** <code>MissionRep</code> is {@link BrowserWindow} representation of {@link Mission}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class MissionRep extends ElementRep {
  
  /** Create new MissionRep as a <em>Singleton</em>.
    * @param mission      The original {@link Mission}.
    * @param browser    The {@link BrowserWindow}. */
  // TBD: do factory for other elements too
  public static MissionRep create(Mission          mission,
                                BrowserWindow  browser) {
    MissionRep missionRep = _missionReps.get(mission.toString());
    if (missionRep == null) {
      missionRep = new MissionRep(mission, browser);
      log.info("Adding Mission " + missionRep);
      _missionReps.put(mission.toString(), missionRep);
      }
    return missionRep;
    }
  
  private static Map<String, MissionRep> _missionReps = new HashMap<>();  
    
  /** Create new MissionRep.
    * Check the progress.
    * @param name       The represented {@link Mission}.
    * @param browser    The {@link BrowserWindow}. */
  public MissionRep(Mission       mission,
                    BrowserWindow browser) {
    super(mission, browser, Images.MISSION);
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
            resultString = batchRep().serverRep().livy().checkProgress(batchRep().id(), mission.id());
            result = new JSONObject(resultString);
            progress = result.getDouble("progress");
            batchRep().setProgress(progress);
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
            batchRep().setResult(text); // check, if the selected tab is correct
            }
          });
        }
      };
    thread.start();
    }
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem show = new MenuItem("Show Batch",  Images.icon(Images.BATCH));
    show.setOnAction(new MissionEventHandler(this));
    menuItems.add(show);
    return menuItems;
    }
  
  /** Give hosting {@link BatchRep}.
    * @return The hosting {@link BatchRep}. */
  public BatchRep batchRep() {
    return BatchRep.create(batch(), browser());
    }
    
  /** Give the associated hosting {@link Batch}.
    * @return The associsted hosting {@link Batch}. */
  public Batch batch() {
    return mission().batch();
    }

  /** Give the associated hosting {@link Batch} id.
    * @return The associated hosting {@link Batch} id. */
  public int id() {
    return mission().id();
    }
    
  /** Give the referenced {@link Mission}.
    * @return The referenced {@link Mission}. */
  public Mission mission() {
    return (Mission)element();
    }
    
  @Override
  public String toString() {
    return mission().toString();
    }
    
  /** Logging . */
  private static Logger log = Logger.getLogger(MissionRep.class);

  }
