package com.astrolabsoftware.AstroLabNet.DB;

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

/** <code>ActionEventHandler</code> implements {@link EventHandler} for {@link Action}.
  * It fills existing {@link Session}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ActionEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param action The {@link Server} to run on. */
  public ActionEventHandler(Action action) {
    _action = action;
    }
 
  @Override
  public void handle(ActionEvent event) {
    Session selected = _action.browser().getSelectedSession();
    if (selected == null) {
      log.error("No Session is selected");
      }
    else if (selected.language() != _action.language()) {
      log.error("Action language " + _action.language() + " != Session language " + selected.language());
      }
    else {
      _action.browser().setSessionCmd(_action.cmd());
      }
    }
 
  private Action _action;  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(ActionEventHandler.class);
    
  }
