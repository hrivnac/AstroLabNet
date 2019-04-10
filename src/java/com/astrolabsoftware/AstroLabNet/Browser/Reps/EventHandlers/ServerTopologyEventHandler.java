package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.HBaser.HBaseClient;

// JavaFX
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;

/** <code>ServerTopologyEventHandler</code> implements {@link EventHandler} for {@link ServerRep}.
  * It handles <em>Topology</em> database.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ServerTopologyEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param serverRep The associated {@link ServerRep}. */
  public ServerTopologyEventHandler(ServerRep serverRep) {
    _browser = serverRep.browser();
    _hbase   = serverRep.hbase();
    }
 
  @Override
  public void handle(ActionEvent event) {
    // Desc
    Label desc = new HeaderLabel("Topology Search", "Search Topology Database");
    // Cmd
    TextArea cmd = new TextArea();
    cmd.setPrefHeight(2000);
    // Search
    Button search = new SimpleButton("Search", "Search Topology Database");
    // ButtonBox = Search
    HBox buttonBox = new HBox(10);
    buttonBox.setSpacing(5);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().addAll(search);
    // CmdBox = Desc + Cmd + ButtonBox 
    VBox cmdBox = new VBox();
    cmdBox.setSpacing(5);
    cmdBox.setAlignment(Pos.CENTER);
    cmdBox.getChildren().addAll(desc, cmd, buttonBox);
    // ResultText
    TextFlow resultText = new TextFlow(); 
    Text result0 = new Text();
    result0.setFill(Color.DARKGREEN);
    resultText.getChildren().add(result0);
    // ScrollPane = ResultText
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setFitToWidth(true);
    scrollPane.setContent(resultText);
    // Pane = Desc + Cmd + ButtonBox + ScrollPane
    SplitPane pane = new SplitPane();
    pane.setDividerPositions(0.5);
    pane.setOrientation(Orientation.VERTICAL);
    pane.getItems().addAll(cmdBox, scrollPane);
    // Actions
    search.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        resultText.getChildren().add(new Text(_hbase.scan("astrolabnet.topology.1", null, 0, 0, 0)));
        }
      });
    // Show
    Tab tab = _browser.addTab(pane, toString(), Images.TOPOLOGY);
    }
  
  private BrowserWindow _browser;  
    
  private HBaseClient _hbase;
    
  }
