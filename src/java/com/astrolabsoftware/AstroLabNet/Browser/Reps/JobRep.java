package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.scene.control.MenuItem;

// Java
import java.util.List;

// Log4J
import org.apache.log4j.Logger;

/** <code>JobRep</code> is {@link BrowserWindow} representation of {@link Job}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class JobRep extends ElementRep {
  
  /** Create new JobRep.
    * @param job      The represented {@link Job}.
    * @param browser  The {@link BrowserWindow}. */
  public JobRep(Job           job,
                BrowserWindow browser) {
    super(job, browser, Images.JOB);
    }
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem execute = new MenuItem("Use for Batch",  Images.icon(Images.SESSION));
    execute.setOnAction(new JobEventHandler(this));
    menuItems.add(execute);
    return menuItems;
    }
    
  /** Activate {@link Session}. */
  public void activate() {
    BatchRep selected = browser().getSelectedBatch();
    if (selected == null) {
      log.error("No Batch is selected");
      }
    else {
      // TBD: browser().setSessionCmd(cmd());
      }
    }

  /** Give the Job jar filename.
    * @return the Job jar filename.. */
  public String file() {
    return job().file();
    }
    
  /** Give the Job <em>main</em> className.
    * @return The <em>main</em> className. */
  public String className() {
    return job().className();
    }
    
  /** Give the referenced {@link Job}.
    * @return The referenced {@link Job}. */
  public Job job() {
    return (Job)element();
    }
    
  @Override
  public String toString() {
    return job().toString();
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(JobRep.class);

  }
