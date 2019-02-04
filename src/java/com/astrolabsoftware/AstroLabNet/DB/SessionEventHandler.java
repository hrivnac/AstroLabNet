package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

// org.json
import org.json.JSONObject;

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
    * @param session  The associated {@link Session}. */
  public SessionEventHandler(Session session) {
    _session = session;
    }
    
  @Override
  public void handle(ActionEvent event) {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    Label desc = new Label("Command in " + _session.language() + ":");
    grid.add(desc, 0, 0);
    TextArea cmd = new TextArea();
    grid.add(cmd, 0, 1);
    Button button = new Button("Execute");
    HBox buttonBox = new HBox(10);
    buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
    buttonBox.getChildren().add(button);
    grid.add(buttonBox, 0, 2);
    Text actionTarget = new Text("Fill in or select Action");
    grid.add(actionTarget, 0, 3);
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        String result = _session.server().livy().sendCommand(_session.id(), cmd.getText());
        int id = new JSONObject(result).getInt("id");;
        _session.browser().addTask(_session.server().urlLivy() + "/" + _session.id() + "/" + id, _session, id);
        actionTarget.setText("Command send to Session");
        }
      });
    _session.setResultRef(actionTarget);
    Tab tab = _session.browser().addTab(grid, _session.toString(), Images.USE);
    _session.browser().registerSessionTab(_session, tab);
    }
    
  private Session _session;  

  }
