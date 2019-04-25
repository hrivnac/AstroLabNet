package com.astrolabsoftware.AstroLabNet.Browser.Components;

// JavaFX
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.event.EventHandler;

/** <code>SimpleButton</code> provides standard {@link Button}s.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class SimpleButton extends Button {

  /** TBD */
  public SimpleButton(String name,
                      String tooltip) {
    super(name);
    new ToolTipper(this, tooltip);
    }

  /** TBD */
  public SimpleButton(String name,
                      Image image,
                      String tooltip,
                      EventHandler handler) {
    super(name);
    setGraphic(new ImageView(image));
    new ToolTipper(this, tooltip);
    setOnAction(handler);
    }
    
  /** TBD */
  public SimpleButton(String name,
                      Image image,
                      String tooltip) {
    super(name);
    setGraphic(new ImageView(image));
    new ToolTipper(this, tooltip);
    }

  }
