package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

// Log4J
import org.apache.log4j.Logger;

/** <code>JobEventHandler</code> implements {@link EventHandler} for {@link JobRep}.
  * It fills existing {@link SessionRep}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class JobEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param jobRep The {@link JobRep} to run on. */
  public JobEventHandler(JobRep jobRep) {
    _jobRep = jobRep;
    }
 
  @Override
  public void handle(ActionEvent event) {
    _jobRep.activate();
    }
 
  private JobRep _jobRep;  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(JobEventHandler.class);
    
  }
