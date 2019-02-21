package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

// Java

// Log4J
import org.apache.log4j.Logger;

/** <code>TaskEventHandler</code> implements {@link EventHandler} for {@link TaskRep}.
  * It shows related {@link Session}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class TaskEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param taskRep The {@link TaskRep} to run on. */
  public TaskEventHandler(TaskRep taskRep) {
    _taskRep = taskRep;
    }
 
  @Override
  public void handle(ActionEvent event) {
    _taskRep.browser().selectTab(_taskRep.sessionRep());
    }
 
  private TaskRep _taskRep;  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(TaskEventHandler.class);
    
  }
