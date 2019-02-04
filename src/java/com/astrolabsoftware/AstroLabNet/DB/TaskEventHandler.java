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

/** <code>TaskEventHandler</code> implements {@link EventHandler} for {@link Task}.
  * It shows related {@link Session}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class TaskEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param task The {@link Task} to run on. */
  public TaskEventHandler(Task task) {
    _task = task;
    }
 
  @Override
  public void handle(ActionEvent event) {
    _task.browser().selectTab(_task.session());
    }
 
  private Task _task;  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(TaskEventHandler.class);
    
  }
