package com.astrolabsoftware.AstroLabNet.Browser.Components;

// JavaFX
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.Tooltip;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.ContentDisplay;

/** <code>AboutLabel</code> of <em>AstroLabNet</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class AboutLabel extends Label {

  public AboutLabel() {
    super();
    setGraphic(new ImageView(Images.ASTROLAB));
    new ToolTipper(this, "<html><h1> AstroLabNet </h1>"
                       + "<h2>Frond end for the AstroLab</h2>"
                       + "<h3>https://astrolabsoftware.github.io</h3></html>");
    }

  }
