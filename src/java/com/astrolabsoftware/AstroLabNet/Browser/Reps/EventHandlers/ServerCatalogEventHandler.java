package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.DB.Server;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.ServerRep;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.Images;
import com.astrolabsoftware.AstroLabNet.Browser.Components.HeaderLabel;
import com.astrolabsoftware.AstroLabNet.Browser.Components.SimpleButton;
import com.astrolabsoftware.AstroLabNet.HBaser.HBaseClient;
import com.astrolabsoftware.AstroLabNet.Catalog.HBase2Graph;
import com.astrolabsoftware.AstroLabNet.Catalog.ClickManager;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// JavaFX
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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

// Java

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
    // Search
    Button search = new SimpleButton("Search", "Search Catalog Database");
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
    // ResultGraph	  
    Graph graph = new MultiGraph("Catalog");
    FxViewer viewer = new FxViewer(new ThreadProxyPipe(graph));
    try {
		  graph.setAttribute("ui.stylesheet", new StringResource("com/astrolabsoftware/AstroLabNet/Catalog/Catalog.css").toString());
		  }
		catch (AstroLabNetException e) {
		  log.warn("Cannot load GraphStream Stylesheet", e);
		  }
		FxViewPanel graphView = (FxViewPanel)viewer.addDefaultView(true);
		graphView.setMouseManager(new ClickManager(graph));
    graphView.setOnScroll(
      new EventHandler<ScrollEvent>() {
        @Override
        public void handle(ScrollEvent event) {
          double zoomFactor = 1.05;
          double deltaY = event.getDeltaY();          
          if (deltaY < 0) {
            zoomFactor = 0.95;
            }
          graphView.setScaleX(graphView.getScaleX() * zoomFactor);
          graphView.setScaleY(graphView.getScaleY() * zoomFactor);
          event.consume();
          }
      });
		viewer.enableAutoLayout();
    // Pane = Desc + Cmd + ButtonBox + ScrollPane
    SplitPane pane = new SplitPane();
    pane.setDividerPositions(0.5);
    pane.setOrientation(Orientation.VERTICAL);
    pane.getItems().addAll(cmdBox, graphView);
    // Actions
    search.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        JSONObject json = _hbase.scan2JSON("astrolabnet.catalog.1",
                                            null,
                                            0,
                                            0,
                                            0);
        new HBase2Graph().updateGraph(json, graph);
        }
      });
    // Show
    Tab tab = _browser.addTab(pane, toString(), Images.CATALOG);
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
