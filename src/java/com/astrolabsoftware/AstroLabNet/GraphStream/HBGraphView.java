package com.astrolabsoftware.AstroLabNet.GraphStream;

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
import com.astrolabsoftware.AstroLabNet.Journal.JournalEntry;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.CommonException;

// JavaFX
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;

// GraphStream
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.stream.thread.ThreadProxyPipe;
import org.graphstream.stream.SinkAdapter;
import org.graphstream.stream.ProxyPipe;

// Java
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

// Log4J
import org.apache.log4j.Logger;

/** The <em>HBGraphView</em> represents <em>HBase</em> <em>Graph</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class HBGraphView {
  
  /** Create.
    * @param name The name of the View. */
  public HBGraphView(String name) {
    _graph = new MultiGraph(name);
    ThreadProxyPipe pipe = new ThreadProxyPipe(_graph);
    FxViewer viewer = new FxViewer(pipe);
    try {
		  _graph.setAttribute("ui.stylesheet", new StringResource("com/astrolabsoftware/AstroLabNet/GraphStream/Graph.css").toString());
		  }
		catch (CommonException e) {
		  log.warn("Cannot load GraphStream Stylesheet", e);
		  }
		_graphView = (FxViewPanel)viewer.addDefaultView(true);
		_graphView.setMouseManager(new ClickManager(_graph));
    _graphView.setOnScroll(
      new EventHandler<ScrollEvent>() {
        @Override
        public void handle(ScrollEvent event) {
          double zoomFactor = 1.05;
          double deltaY = event.getDeltaY();          
          if (deltaY < 0) {
            zoomFactor = 0.95;
            }
          _graphView.setScaleX(_graphView.getScaleX() * zoomFactor);
          _graphView.setScaleY(_graphView.getScaleY() * zoomFactor);
          event.consume();
          }
      });
		viewer.enableAutoLayout();
	  }
		
	/** Give the contained {@link Graph}.
	  * @return The contained {@link Graph}. */
  public Graph graph() {
    return _graph;
    }
	  
  /** Give the associated {@link FxViewPanel}.
    * @return The associated {@link FxViewPanel}. */
  public FxViewPanel graphView() {
    return _graphView;
    }
    
	private Graph _graph;  
	
	private FxViewPanel _graphView;
	  
  /** Logging . */
  private static Logger log = Logger.getLogger(HBGraphView.class);
 
  }
