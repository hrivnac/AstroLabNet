package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Livyser.LivyRESTClient;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// JavaFX
import javafx.scene.control.TreeItem;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.util.Pair;

// Java
import java.util.List;

// Log4J
import org.apache.log4j.Logger;

/** <code>ServerEventHandler</code> implements {@link EventHandler} for {@link ServerRep}.
  * It creates new {@link Session}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ServerEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param serverRep The associated {@link ServerRep}.
    * @param language  The {@link Language} of this {@link EventHandler}. */
  public ServerEventHandler(ServerRep serverRep,
                            Language  language) {
    _serverRep = serverRep;
    _language  = language;
    }
 
  @Override
  public void handle(ActionEvent event) {
    _serverRep.livy().initSession(_language);
    _serverRep.updateSessions();
    }
 
  private Language _language;  
  
  private ServerRep _serverRep;
    
  }
