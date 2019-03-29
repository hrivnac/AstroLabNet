package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/** <code>ServerBatchEventHandler</code> implements {@link EventHandler} for {@link ServerRep}.
  * It creates new {@link Batch}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ServerBatchEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param serverRep The associated {@link ServerRep}. */
  public ServerBatchEventHandler(ServerRep serverRep) {
    _serverRep = serverRep;
    }
 
  @Override
  public void handle(ActionEvent event) {
    //_serverRep.livy().initBatch(_file, _className, Integer.MAX_VALUE, 1);
    _serverRep.updateBatchs();
    }
    
  private ServerRep _serverRep;
    
  }
