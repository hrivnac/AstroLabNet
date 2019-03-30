package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.ScrollPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;

// Java
import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes;
import java.util.Iterator;

// Log4J
import org.apache.log4j.Logger;

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
    BatchRep batchRep = new BatchRep(new Batch("xxx", _serverRep.server()), _serverRep.browser());
    batchRep.addTab();
    _serverRep.updateBatchs();
    }
     
  private ServerRep _serverRep;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(ServerBatchEventHandler.class);
    
  }
