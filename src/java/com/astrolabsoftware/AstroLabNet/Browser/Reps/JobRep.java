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
    MenuItem execute = new MenuItem("Use for Session",  Images.icon(Images.SESSION));
    execute.setOnAction(new JobEventHandler(this));
    menuItems.add(execute);
    return menuItems;
    }
    
  /** Activate {@link Session}. */
  public void activate() {
    SessionRep selected = browser().getSelectedSession();
    if (selected == null) {
      log.error("No Session is selected");
      }
    else if (selected.language() != language()) {
      log.error("Job language " + language() + " != Session language " + selected.language());
      }
    else {
      browser().setSessionCmd(cmd());
      }
    }

  /** Give the associated command text.
    * @return The associated command text. */
  public String cmd() {
    return job().cmd();
    }
    
  /** Give the Job {@link Language}.
    * @return The Action {@link Language}. */
  public Language language() {
    return job().language();
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
