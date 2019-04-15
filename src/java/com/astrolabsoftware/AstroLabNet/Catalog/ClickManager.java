package com.astrolabsoftware.AstroLabNet.Catalog;

import com.astrolabsoftware.AstroLabNet.Utils.Coding;

// GraphStream
import org.graphstream.graph.Graph;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.fx_viewer.util.FxMouseManager;
import org.graphstream.graph.ElementNotFoundException;

// org.json
import org.json.JSONObject;
import org.json.JSONArray;

// JavaFx
import javafx.scene.input.MouseEvent;

// Java
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

// Log4J
import org.apache.log4j.Logger;

/** <code>ClickManager</code> handles <em>click</em> on {@link Graph}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ClickManager extends FxMouseManager {
  
  /** TBD */
  public ClickManager(Graph graph) {
    super();
    _graph = graph;
    }
     
  /** TBD */
  protected void mouseButtonPressOnElement(GraphicElement element,
											                      MouseEvent     event) {
	  log.info(_graph.getNode(element.getId()).getAttribute("content"));
	  super.mouseButtonPressOnElement(element, event);
	  }
	  
	private Graph _graph;
	  
  /** Logging . */
  private static Logger log = Logger.getLogger(Click.class);

  }
