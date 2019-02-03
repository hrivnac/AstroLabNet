package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

// Java
import java.util.List;

// Log4J
import org.apache.log4j.Logger;

/** <code>SessionEventHandler</code> implements {@link EventHandler} for {@link Session}.
  * It executes {@link Action}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class SessionEventHandler implements EventHandler<ActionEvent> {
  
  /** Create.
    * @param title    The title of the {@link Tab}.
    * @param language The {@link Language} of {@link Session}.
    * @param browser  The {@link WindowBrowser} to use. */
  public SessionEventHandler(String        title,
                             Language      language,
                             BrowserWindow browser) {
    _title    = title;
    _language = language;
    _browser  = browser;
    }
    
  @Override
  public void handle(ActionEvent event) {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    Label desc = new Label("Command in " + _language + ":");
    grid.add(desc, 0, 0);
    TextField cmd = new TextField();
    grid.add(cmd, 0, 1);
    Button button = new Button("Execute");
    HBox buttonBox = new HBox(10);
    buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
    buttonBox.getChildren().add(button);
    grid.add(buttonBox, 0, 2);
    Text actiontarget = new Text("Fill in or select Action");
    grid.add(actiontarget, 1, 2);
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        actiontarget.setFill(Color.FIREBRICK);
        actiontarget.setText("Command send to Session");
        }
      });
    _browser.addTab(grid, _title, Images.USE);
    _browser.registerSessionCmd(cmd);
    }
    
  private String _title;
  
  private Language _language;
  
  private BrowserWindow _browser;

  }
