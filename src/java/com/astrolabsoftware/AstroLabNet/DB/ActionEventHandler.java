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

/** <code>ActionEventHandler</code> implements {@link EventHandler} for {@link Action}.
  * It fills existing {@link Session}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ActionEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param language The {@link Language} of new {@link Session}.
    * @param action   The {@link Server} to run on.
    * @param livy     The {@LivyRI} server. */
  public ActionEventHandler(Action        action,
                            BrowserWindow browser) {
    _action  = action;
    _browser = browser;
    }
 
  @Override
  public void handle(ActionEvent event) {
    if (_browser.sessionCmd() == null) {
      log.error("No Session is opened");
      }
    else {
      _browser.setSessionCmd(_action.cmd());
      }
    }
 
  private Action _action;  
  
  private BrowserWindow _browser;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(ActionEventHandler.class);
    
  }
