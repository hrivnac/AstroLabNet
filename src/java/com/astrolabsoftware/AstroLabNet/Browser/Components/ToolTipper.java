package com.astrolabsoftware.AstroLabNet.Browser.Components;

// JavaFX
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.Tooltip;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.ContentDisplay;
import javafx.scene.Node;

/** <code>ToolTipper</code> adds {@link Tooltip} noe {@link Node}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class ToolTipper {

  public ToolTipper(Node node,
                    String text) {
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    webEngine.loadContent(text); 
    Tooltip tooltip = new Tooltip();
    tooltip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    tooltip.setGraphic(webView);
    Tooltip.install(node, tooltip);
    }

  }
