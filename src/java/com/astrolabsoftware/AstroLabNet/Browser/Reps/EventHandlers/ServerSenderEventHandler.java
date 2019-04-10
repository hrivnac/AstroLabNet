package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.DB.Sender;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.ServerRep;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/** <code>ServerSenderEventHandler</code> implements {@link EventHandler} for {@link ServerRep}.
  * It creates new {@link Sender}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ServerSenderEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param serverRep The associated {@link ServerRep}. */
  public ServerSenderEventHandler(ServerRep serverRep) {
    _serverRep = serverRep;
    }
 
  @Override
  public void handle(ActionEvent event) {
     new Sender("Sender", _serverRep.server()).register();
    _serverRep.update();
    }
  
  private ServerRep _serverRep;
    
  }
