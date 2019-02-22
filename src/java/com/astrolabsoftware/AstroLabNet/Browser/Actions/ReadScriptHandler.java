package com.astrolabsoftware.AstroLabNet.Browser.Actions;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Utils.StringFile;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// JavaFX
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Java
import java.io.File;

// Log4J
import org.apache.log4j.Logger;

/** <code>ReadScriptHandler</code> handles reading and sourcing BSH script.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class ReadScriptHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param w The connected {@link BrowserWindow}. */
  public ReadScriptHandler(BrowserWindow w) {
    super();
    _w = w;
    }
    
  /** Close the {@link BrowserWindow}.
    * @param event The acted {@link ActionEvent}. */
  @Override
  public void handle(ActionEvent event) {
    Stage dialog = new Stage();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open BSH File");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("BeanShell File", "*.bsh"));
    dialog.initStyle(StageStyle.UTILITY);
    File selectedFile = fileChooser.showOpenDialog(dialog);
    if (selectedFile != null) {
      try {
        String text = new StringFile(selectedFile).toString();
        _w.command().interpret(text);
        }
      catch (AstroLabNetException e) {
        log.error("Cannot read " + selectedFile);
        }
      }
    }
    
  private BrowserWindow _w;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(ReadScriptHandler.class);

  }
