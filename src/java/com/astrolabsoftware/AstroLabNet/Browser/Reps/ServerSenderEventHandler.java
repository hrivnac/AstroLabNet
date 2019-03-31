package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;

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
    _serverRep.updateSenders();
    }
  
  private ServerRep _serverRep;
    
  }
