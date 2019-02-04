package com.astrolabsoftware.AstroLabNet.Browser.Components;

// JavaFX
import javafx.scene.control.Tooltip;
import javafx.scene.control.Control;
import javafx.scene.control.ContentDisplay;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.Node;

/** <code>ToolTipper</code> adds {@link Tooltip} noe {@link Node}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class ToolTipper {

  /** Add a tooltip to the {@link Node}.
    * Only shows text.
    * @param node The {@link Node} to receive the tooltip.
    * @param text The text of the tooltip, can contain HTML. */
   public ToolTipper(Control node,
                     String text) {
     new ToolTipper(node, text, false);
     }
  
  /** Add a tooltip to the {@link Node}.
    * @param node The {@link Node} to receive the tooltip.
    * @param text The text of the tooltip, can contain HTML.
    * @param html Whether should show HTML text. */
  public ToolTipper(Control node,
                    String text,
                    boolean html) {
    Tooltip tooltip = new Tooltip();
    tooltip.setStyle("-fx-font: normal bold 20 Langdon;" +
                     "-fx-background-color: #ffff00;" +
                     "-fx-text-fill: #ff0000;");
    if (html) {
      WebView webView = new WebView();
      WebEngine webEngine = webView.getEngine();
      webEngine.loadContent(text); 
      tooltip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      tooltip.setGraphic(webView);
      Tooltip.install(node, tooltip);
      }
    else {
      tooltip.setText(text);
      node.setTooltip(tooltip);    
      }
    }

  }
