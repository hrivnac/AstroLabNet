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
  // TBD: check language compatibility
  public void handle(ActionEvent event) {
    _action.browser().setSessionCmd(_action.cmd());
    }
 
  private Action _action;  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(ActionEventHandler.class);
    
  }
