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
    * @param serverRep The associated {@link ServerRep}.
    * @param language  The {@link Language} of this {@link EventHandler}. */
  public ServerBatchEventHandler(ServerRep serverRep,
                                   Language  language) {
    _serverRep = serverRep;
    _language  = language;
    }
 
  @Override
  public void handle(ActionEvent event) {
    _serverRep.livy().initBatch(_language, Integer.MAX_VALUE, 1);
    _serverRep.updateBatchs();
    }
 
  private Language _language;  
  
  private ServerRep _serverRep;
    
  }
