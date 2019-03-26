package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

// Java

// Log4J

/** <code>ServerSourceEventHandler</code> implements {@link EventHandler} for {@link ServerRep}.
  * It creates new {@link Source}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ServerSourceEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param serverRep The associated {@link ServerRep}. */
  public ServerSourceEventHandler(ServerRep serverRep) {
    _serverRep = serverRep;
    }
 
  @Override
  public void handle(ActionEvent event) {
    _serverRep.showSource();
    }
  
  private ServerRep _serverRep;
    
  }
