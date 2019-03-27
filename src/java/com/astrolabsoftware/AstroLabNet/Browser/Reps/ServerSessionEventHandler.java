package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/** <code>ServerSessionEventHandler</code> implements {@link EventHandler} for {@link ServerRep}.
  * It creates new {@link Session}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ServerSessionEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param serverRep The associated {@link ServerRep}.
    * @param language  The {@link Language} of this {@link EventHandler}. */
  public ServerSessionEventHandler(ServerRep serverRep,
                                   Language  language) {
    _serverRep = serverRep;
    _language  = language;
    }
 
  @Override
  public void handle(ActionEvent event) {
    _serverRep.livy().initSession(_language, Integer.MAX_VALUE, 1);
    _serverRep.updateSessions();
    }
 
  private Language _language;  
  
  private ServerRep _serverRep;
    
  }
