package com.astrolabsoftware.AstroLabNet.Browser.Components;

// JavaFX
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
    new ToolTipper(this, "AstroLabNet: Frond end for the AstroLab");
    }

  }
