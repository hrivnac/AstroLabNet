package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

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
    
  }
