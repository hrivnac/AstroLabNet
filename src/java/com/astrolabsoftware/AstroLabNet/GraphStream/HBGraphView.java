package com.astrolabsoftware.AstroLabNet.GraphStream;

import com.astrolabsoftware.AstroLabNet.GraphStream.ClickManager;
import com.astrolabsoftware.AstroLabNet.GraphStream.HBGraphView;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.CommonException;

// JavaFX
import javafx.scene.input.ScrollEvent;
import javafx.event.EventHandler;

// GraphStream
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.stream.thread.ThreadProxyPipe;

// Java

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
