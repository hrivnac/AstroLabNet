package com.astrolabsoftware.AstroLabNet.Browser.Components;

// JavaFX
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

/** <code>HeaderLabel</code>..
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class HeaderLabel extends Label {

  public HeaderLabel(String text,
                     String toolTip) {
    super(text);
    setFont(Fonts.HEADER);
    setTextFill(Color.web("#0076a3")); 
    new ToolTipper(this, toolTip);
    }

  }
