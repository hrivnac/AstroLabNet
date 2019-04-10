package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.Browser.Components.Images;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserCommand;

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

/** <code>ServersEventHandler</code> implements {@link EventHandler} for group of {@link ServerRep}s.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ServersEventHandler implements EventHandler<ActionEvent> {

  /** Create. */
  public ServersEventHandler(BrowserCommand command) {
    _command = command;
    }
 
  @Override
  public void handle(ActionEvent event) {
    Stage dialog = new Stage();
    Label titleLabel = new Label("Create New Server");
    titleLabel.setGraphic(new ImageView(Images.SPARK));
    Label nameLabel = new Label("name:");
    TextField nameField = new TextField();
    Label livyLabel = new Label("Livy Server:");
    TextField livyField = new TextField("http://");
    Label sparkLabel = new Label("Spark Server:");
    TextField sparkField = new TextField("http://");
    Label hbaseLabel = new Label("HBase Server:");
    TextField hbaseField = new TextField("http://");
    Button create = new Button("create");
    create.setOnAction(new EventHandler() {
      @Override
      public void handle(Event event) {
        _command.addServer(nameField.getText(), livyField.getText(), sparkField.getText(), hbaseField.getText());
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
    HBox livy = new HBox(8);
    livy.getChildren().addAll(livyLabel, livyField);
    HBox spark = new HBox(8);
    spark.getChildren().addAll(sparkLabel, sparkField);
    HBox hbase = new HBox(8);
    hbase.getChildren().addAll(hbaseLabel, hbaseField);
    HBox buttons = new HBox(8);
    buttons.getChildren().addAll(create, close);
    VBox root = new VBox(8);
    root.getChildren().addAll(titleLabel, name, livy, spark, hbase, buttons);
    dialog.initStyle(StageStyle.UTILITY);
    Scene scene = new Scene(root);
    dialog.setScene(scene);
    dialog.show();       
    }
    
  private BrowserCommand _command;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(ServersEventHandler.class);
    
  }
