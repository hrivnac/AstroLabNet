package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.DB.Server;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.ServerRep;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.Images;
import com.astrolabsoftware.AstroLabNet.Browser.Components.HeaderLabel;
import com.astrolabsoftware.AstroLabNet.Browser.Components.SimpleButton;
import com.astrolabsoftware.AstroLabNet.HBaser.HBaseClient;
import com.astrolabsoftware.AstroLabNet.HBaser.HBaseTableView;
import com.astrolabsoftware.AstroLabNet.GraphStream.HBase2Graph;
import com.astrolabsoftware.AstroLabNet.GraphStream.ClickManager;
import com.astrolabsoftware.AstroLabNet.GraphStream.HBGraphView;
import com.astrolabsoftware.AstroLabNet.Topology.TopologyEntry;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// JavaFX
import javafx.scene.text.Text;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;

// GraphStream
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.stream.thread.ThreadProxyPipe;

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
    _server  = serverRep.server();
    _hbase   = serverRep.hbase();
    }
 
  @Override
  public void handle(ActionEvent event) {
    // Desc
    Label desc = new HeaderLabel(toString(), "Search Topology Database");
    // Cmd
    TextArea cmd = new TextArea();
    cmd.setPrefHeight(2000);
    // Search as Table
    Button searchTable = new SimpleButton("Search as table", "Search Topology Database and present as a table");
    // Search as Graph
    Button searchGraph = new SimpleButton("Search as graph", "Search Topology Database and present as a graph");
    // ButtonBox = SearchTable + SearchGraph
    HBox buttonBox = new HBox(10);
    buttonBox.setSpacing(5);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().addAll(searchTable, searchGraph);
    // CmdBox = Desc + Cmd + ButtonBox 
    VBox cmdBox = new VBox();
    cmdBox.setSpacing(5);
    cmdBox.setAlignment(Pos.CENTER);
    cmdBox.getChildren().addAll(desc, cmd, buttonBox);
    // ResultTable
    HBaseTableView<TopologyEntry> resultTable = new HBaseTableView<>(); 
    resultTable.setEntryNames(TopologyEntry.ENTRY_NAMES);
    // ResultGraph	  
    HBGraphView resultGraph = new HBGraphView("Topology"); 
    // Pane = CmdBox + ...
    SplitPane pane = new SplitPane();
    pane.setDividerPositions(0.5);
    pane.setOrientation(Orientation.VERTICAL);
    pane.getItems().addAll(cmdBox);
    // Actions
    searchTable.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        JSONObject json = search();
        resultTable.getItems().clear();
        resultTable.addJSONEntry(json, TopologyEntry.class);
        pane.getItems().addAll(resultTable);
        resultTable.refresh();
        }
      });
    searchGraph.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        JSONObject json = search();
        pane.getItems().addAll(resultGraph.graphView());
        new HBase2Graph().updateGraph(json, resultGraph.graph());
        }
      });
    // Show
    Tab tab = _browser.addTab(pane, toString(), Images.TOPOLOGY);
    }
    
  /** TBD */
  private JSONObject search() {
    JSONObject json = _hbase.scan2JSON("astrolabnet.topology.1",
                                       null,
                                       0,
                                       0,
                                       0);
    return json;
    }
    
  @Override
  public String toString() {
    return "Topology on " + _server.name();
    }
  
  private BrowserWindow _browser;  
    
  private Server _server;
    
  private HBaseClient _hbase;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(ServerTopologyEventHandler.class);
   
  }
