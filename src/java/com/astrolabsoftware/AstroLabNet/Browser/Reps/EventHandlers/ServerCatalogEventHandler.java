package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.DB.Server;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.ServerRep;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.Images;
import com.astrolabsoftware.AstroLabNet.Browser.Components.HeaderLabel;
import com.astrolabsoftware.AstroLabNet.Browser.Components.SimpleButton;
import com.astrolabsoftware.AstroLabNet.HBaser.HBaseClient;

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

// GraphStream
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.fx_viewer.util.DefaultApplication;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.javafx.util.ImageCache;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

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
    MultiGraph graph  = new MultiGraph( "TestSize" );
	  Viewer viewer = new FxViewer(graph, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD );
	  ViewerPipe pipeIn = viewer.newViewerPipe();
	  FxDefaultView resultGraph = (FxDefaultView)viewer.addView("GraphView", new FxGraphRenderer() );
    // Pane = Desc + Cmd + ButtonBox + ScrollPane
    SplitPane pane = new SplitPane();
    pane.setDividerPositions(0.5);
    pane.setOrientation(Orientation.VERTICAL);
    pane.getItems().addAll(cmdBox, resultGraph);
    // Actions
    search.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        //resultText.getChildren().add(new Text(_hbase.scan("astrolabnet.catalog.1", null, 0, 0, 0)));
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
    
  }
