package com.astrolabsoftware.AstroLabNet.Browser.Components;

// JavaFX
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.event.EventHandler;

/** <code>Fonts</code> provides standard {@link Button}s.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class SimpleButton extends Button {

  public SimpleButton(String name,
                      String tooltip,
                      EventHandler handler) {
    super(name);
    setGraphic(new ImageView(Images.ASTROLAB));
    new ToolTipper(this, tooltip);
    setOnAction(handler);
    }

  public SimpleButton(String name,
                      Image image,
                      String tooltip,
                      EventHandler handler) {
    super(name);
    setGraphic(new ImageView(image));
    new ToolTipper(this, tooltip);
    setOnAction(handler);
    }

/*    
    
  public SimpleButton(String name,
                      int alignment,
                      Font font,
                      String tip,
                      ActionListener listener) {
    super(name);
    setHorizontalTextPosition(AbstractButton.CENTER);
    setFont(font);
    if (tip != null) {
      setToolTipText(tip);
      }
	  addActionListener(listener);
    }

  public SimpleButton(String name,
                      ImageIcon icon,
                      int alignment,
                      Font font,
                      Dimension size,
                      String tip,
                      ActionListener listener) {
    super(name, icon); 
    setHorizontalTextPosition(AbstractButton.CENTER);
    setForeground(getBackground()); 
    setMinimumSize(size);
    setMaximumSize(size);
    setFont(font);
    if (tip != null) {
      setToolTipText(tip);
      }
	  addActionListener(listener);
    }
*/
  }
