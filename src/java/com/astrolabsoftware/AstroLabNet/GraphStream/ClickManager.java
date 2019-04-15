package com.astrolabsoftware.AstroLabNet.GraphStream;

// GraphStream
import org.graphstream.graph.Graph;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.fx_viewer.util.FxMouseManager;

// JavaFx
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox; 
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

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
     
  /** React to pressed mouse. Open PopUp window with element content.
    * @param element The clicked {@link GraphElement}.
    * @param event   The acting {@link MouseEvent}. */
  protected void mouseButtonPressOnElement(GraphicElement element,
											                      MouseEvent     event) {
    final Stage dialog = new Stage();
    dialog.initModality(Modality.WINDOW_MODAL);
    Text content = new Text(_graph.getNode(element.getId()).getAttribute("content").toString());
    Button button = new Button("Close");
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        dialog.close();
        }
      }); 
    VBox dialogVBox = new VBox(20);
    dialogVBox.setMargin(content, new Insets(10));
    dialogVBox.setMargin(button, new Insets(10));
    dialogVBox.getChildren().addAll(content, button);
    Scene dialogScene = new Scene(dialogVBox, 300, 200);
    dialog.setScene(dialogScene);
    dialog.show();	  
	  super.mouseButtonPressOnElement(element, event);
	  }
	  
	private Graph _graph;
	  
  /** Logging . */
  private static Logger log = Logger.getLogger(ClickManager.class);

  }
