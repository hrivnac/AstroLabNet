package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.Browser.Reps.SessionRep;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/** <code>SessionEventHandler</code> implements {@link EventHandler} for {@link SessionRep}.
  * It executes {@link Action}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class SessionEventHandler implements EventHandler<ActionEvent> {
  
  /** Create.
    * @param sessionRep The associated {@link SessionRep}. */
  public SessionEventHandler(SessionRep sessionRep) {
    _sessionRep = sessionRep;
    }
    
  @Override
  public void handle(ActionEvent event) {
    _sessionRep.addTab();
    }
    
  private SessionRep _sessionRep;  

  }
