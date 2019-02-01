package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Livyser.LivyRI;
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

/** <code>ServerEventHandler</code> implements {@link EventHandler} for {@link Server}.
  * It creates new {@link Session}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ServerEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param language The {@link Language} of new {@link Session}.
    * @param server   The {@link Server} to run on.
    * @param livy     The {@LivyRI} server. */
  public ServerEventHandler(Language language,
                            Server   server,
                            LivyRI   livy) {
    _language = language;
    _server   = server;
    _livy     = livy;
    }
 
  @Override
  public void handle(ActionEvent event) {
    _livy.initSession(_language);
    _server.updateSessions();
    }
 
  private Language _language;  
  
  private Server _server;
  
  private LivyRI _livy;
    
  }
