package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserCommand;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox; 
import javafx.scene.layout.VBox; 
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Log4J
import org.apache.log4j.Logger;

/** <code>JobsEventHandler</code> implements {@link EventHandler} for group of {@link JobRep}s.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class JobsEventHandler implements EventHandler<ActionEvent> {

  /** Create. */
  public JobsEventHandler(BrowserCommand command) {
    _command = command;
    }
 
  @Override
  public void handle(ActionEvent event) {
    Stage dialog = new Stage();
    Label titleLabel = new Label("Create New Job");
    titleLabel.setGraphic(new ImageView(Images.JOB));
    Label nameLabel = new Label("name:");
    TextField nameField = new TextField();
    Button python = new Button("as Python");
    python.setOnAction(new EventHandler() {
      @Override
      public void handle(Event event) {
        _command.addAction(nameField.getText(), "", Language.PYTHON);
        dialog.close();
        }
      });
    Button scala = new Button("as Scala");
    scala.setOnAction(new EventHandler() {
      @Override
      public void handle(Event event) {
        _command.addAction(nameField.getText(), "", Language.SCALA);
        dialog.close();
        }
      });
    Button sql = new Button("as SQL");
    sql.setOnAction(new EventHandler() {
      @Override
      public void handle(Event event) {
        _command.addAction(nameField.getText(), "", Language.SQL);
        dialog.close();
        }
      });
    Button r = new Button("as R");
    r.setOnAction(new EventHandler() {
      @Override
      public void handle(Event event) {
        _command.addAction(nameField.getText(), "", Language.R);
        dialog.close();
        }
      });
    Button close = new Button("Close");
    close.setOnAction(new EventHandler() {
      @Override
      public void handle(Event event) {
        dialog.close();
        }
      });
    HBox name = new HBox(8);
    name.getChildren().addAll(nameLabel, nameField);
    HBox buttons = new HBox(8);
    buttons.getChildren().addAll(python, scala, sql, r);
    VBox root = new VBox(8);
    root.getChildren().addAll(titleLabel, name, buttons, close);
    dialog.initStyle(StageStyle.UTILITY);
    Scene scene = new Scene(root);
    dialog.setScene(scene);
    dialog.show();       
    }
    
  private BrowserCommand _command;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(JobsEventHandler.class);
    
  }
