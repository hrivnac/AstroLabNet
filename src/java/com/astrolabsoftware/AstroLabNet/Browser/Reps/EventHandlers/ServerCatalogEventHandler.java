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
import com.astrolabsoftware.AstroLabNet.GraphStream.HBGraphView;
import com.astrolabsoftware.AstroLabNet.Catalog.CatalogEntry;

// JavaFX
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;

// GraphStream
import org.graphstream.graph.Graph;

// org.json
import org.json.JSONObject;

// Log4J
import org.apache.log4j.Logger;

/** <code>ServerCatalogEventHandler</code> implements {@link EventHandler} for {@link ServerRep}.
  * It handles <em>Catalog</em> database.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ServerCatalogEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param serverRep The associated {@link ServerRep}. */
  public ServerCatalogEventHandler(ServerRep serverRep) {
    _browser = serverRep.browser();
    _server  = serverRep.server();
    _hbase   = serverRep.hbase();
    }
 
  @Override
  public void handle(ActionEvent event) {
    // Desc
    Label desc = new HeaderLabel(toString(), "Search Catalog Database");
    // Cmd
    TextArea cmd = new TextArea();
    cmd.setPrefHeight(2000);
    // Search as Table
    Button searchTable = new SimpleButton("Search as Table", "Search Catalog Database and present as a table");
    // Search as Graph
    Button searchGraph = new SimpleButton("Search as Graph", "Search Catalog Database and present as a graph");
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
    HBaseTableView<CatalogEntry> resultTable = new HBaseTableView<>(); 
    resultTable.setEntryNames(CatalogEntry.ENTRY_NAMES);
    // ResultGraph	  
     HBGraphView resultGraph = new HBGraphView("Catalog"); 
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
        resultTable.addJSONEntry(json, CatalogEntry.class);
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
    Tab tab = _browser.addTab(pane, toString(), Images.CATALOG);
    }
    
  /** TBD */
  private JSONObject search() {
    JSONObject json = _hbase.scan2JSON("astrolabnet.catalog.1",
                                       null,
                                       0,
                                       0,
                                       0);
    return json;
    }
    
  @Override
  public String toString() {
    return "Catalog on " + _server.name();
    }

  private BrowserWindow _browser;  
    
  private Server _server;
    
  private HBaseClient _hbase;

  /** Logging . */
  private static Logger log = Logger.getLogger(ServerCatalogEventHandler.class);
    
  }
