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

/** <code>JobEventHandler</code> implements {@link EventHandler} for {@link Job}.
  * It shows related {@link Session}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class JobEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param job The {@link Job} to run on. */
  public JobEventHandler(Job job) {
    _job = job;
    }
 
  @Override
  public void handle(ActionEvent event) {
    _job.browser().selectTab(_job.session());
    }
 
  private Job _job;  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(JobEventHandler.class);
    
  }
