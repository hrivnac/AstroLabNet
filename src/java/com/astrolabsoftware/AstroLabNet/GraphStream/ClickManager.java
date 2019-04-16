package com.astrolabsoftware.AstroLabNet.GraphStream;

// GraphStream
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.fx_viewer.util.FxMouseManager;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.stream.thread.ThreadProxyPipe;
import org.graphstream.stream.SinkAdapter;
import org.graphstream.stream.ProxyPipe;

// JavaFx
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox; 
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

// Java
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

// Log4J
import org.apache.log4j.Logger;

/** <code>ClickManager</code> handles <em>click</em> on {@link Graph}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ClickManager extends FxMouseManager {
  
  /** Create.
    @param graph The connected {@link Graph}. */
  public ClickManager(Graph graph) {
    super();
    _graph = graph;
    }
     
  /** React to pressed mouse.
    * Open PopUp window with element content on right  {@link MouseButton}.
	  * Collapse/expand {@link Node} on the middle {@link MouseButton}.
    * @param element The clicked {@link GraphElement}.
    * @param event   The acting {@link MouseEvent}. */
  protected void mouseButtonPressOnElement(GraphicElement element,
											                      MouseEvent     event) {
	  if (event.getButton() == MouseButton.MIDDLE) {
	    toggleNode(element.getId());
	    }
	  else if (event.getButton() == MouseButton.SECONDARY) {
      final Stage dialog = new Stage();
      dialog.initModality(Modality.WINDOW_MODAL);
      String contentS = _graph.getNode(element.getId()).getAttribute("content").toString();
      log.info(contentS);
      Text content = new Text(contentS);
      Button button = new Button("Close");
      button.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          dialog.close();
          }
        }); 
      VBox dialogVBox = new VBox();
      dialogVBox.setMargin(content, new Insets(10));
      dialogVBox.setMargin(button, new Insets(10));
      dialogVBox.getChildren().addAll(content, button);
      Scene dialogScene = new Scene(dialogVBox, 300, 200);
      dialog.setScene(dialogScene);
      dialog.show();	  
	    super.mouseButtonPressOnElement(element, event);
	    }
	  }
	  
	/** Collapse/expand {@link Node} on the middle {@link MouseButton}.
	  * @param id The {@link Node} id. */
	// TBD: recursive
	// TBD: better graphics
  private void toggleNode(String id) {
    Node n  = _graph.getNode(id);
    if (n.hasAttribute("collapsed")) {
      n.removeAttribute("collapsed");
      int in = n.getOutDegree();
      Edge e;
      for (int i = 0; i < n.getOutDegree(); i++) {
        e = n.getLeavingEdge(i);
        e.removeAttribute("ui.hide");
        Node m = e.getNode1();
        m.removeAttribute("layout.frozen");
        m.removeAttribute("ui.hide");
        }
      n.removeAttribute("layout.frozen");
      n.removeAttribute("ui.hide");
      n.removeAttribute("ui.class");          
      }
    else {
      n.setAttribute("ui.class", "plus");
      n.setAttribute("collapsed");          
      int in = n.getOutDegree();
      Edge e;
      for (int i = 0; i < n.getOutDegree(); i++) {
        e = n.getLeavingEdge(i);
        e.setAttribute("ui.hide");
        Node m = e.getNode1();
        m.setAttribute("layout.frozen");
        m.setAttribute("ui.hide");
         }
      }
    }
	  
	private Graph _graph;
	  
  /** Logging . */
  private static Logger log = Logger.getLogger(ClickManager.class);

  }
