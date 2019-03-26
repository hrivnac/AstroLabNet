package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

// Log4J
import org.apache.log4j.Logger;

/** <code>ActionEventHandler</code> implements {@link EventHandler} for {@link ActionRep}.
  * It fills existing {@link SessionRep}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ActionEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param actionRep The {@link ActionRep} to run on. */
  public ActionEventHandler(ActionRep actionRep) {
    _actionRep = actionRep;
    }
 
  @Override
  public void handle(ActionEvent event) {
    _actionRep.activate();
    }
 
  private ActionRep _actionRep;  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(ActionEventHandler.class);
    
  }
