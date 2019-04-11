package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.Browser.Reps.ServerRep;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.Images;
import com.astrolabsoftware.AstroLabNet.Browser.Components.HeaderLabel;
import com.astrolabsoftware.AstroLabNet.Browser.Components.SimpleButton;
import com.astrolabsoftware.AstroLabNet.HBaser.HBaseClient;
import com.astrolabsoftware.AstroLabNet.Journal.TopologyTableView;

// JavaFX
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;

// org.json
import org.json.JSONObject;

// Log4J
import org.apache.log4j.Logger;

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
    // ResultTable
    TopologyTableView resultTable = new TopologyTableView(); 
    // Pane = Desc + Cmd + ButtonBox + ResultTable
    SplitPane pane = new SplitPane();
    pane.setDividerPositions(0.5);
    pane.setOrientation(Orientation.VERTICAL);
    pane.getItems().addAll(cmdBox, resultTable);
    // Actions
    search.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        //resultText.getChildren().add(new Text(_hbase.scan("astrolabnet.topology.1", null, 0, 0, 0)));
        resultTable.getItems().clear();
        JSONObject json = _hbase.scan2JSON("astrolabnet.topology.1",
                                           null,
                                           0,
                                           0,
                                           0);
        resultTable.addJSONEntry(json);
        resultTable.refresh();
        }
      });
    // Show
    Tab tab = _browser.addTab(pane, toString(), Images.TOPOLOGY);
    }
    
  @Override
  public String toString() {
    return "Journal on " + _hbase.toString();
    }
  
  private BrowserWindow _browser;  
    
  private HBaseClient _hbase;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(ServerTopologyEventHandler.class);
   
  }
