package com.astrolabsoftware.AstroLabNet.Browser.Actions;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.SenderRep;

// JavaFX
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

/** <code>ReadJARHandler</code> handles opening JAR files.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class ReadJARHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param h The connected {@link SenderRep}. */
  public ReadJARHandler(SenderRep sr) {
    super();
    _sr = sr;
    }
    
  /** Close the {@link BrowserWindow}.
    * @param event The acted {@link ActionEvent}. */
  @Override
  public void handle(ActionEvent event) {
    Stage dialog = new Stage();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open JAR File");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JAR File", "*.jar"));
    dialog.initStyle(StageStyle.UTILITY);
    File selectedFile = fileChooser.showOpenDialog(dialog);
    if (selectedFile != null) {
      _sr.setFile(selectedFile);
      }
    }
    
  private SenderRep _sr;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(ReadJARHandler.class);

  }
