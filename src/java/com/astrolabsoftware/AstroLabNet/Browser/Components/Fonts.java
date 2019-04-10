package com.astrolabsoftware.AstroLabNet.Browser.Components;

// JavaFX
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/** <code>Fonts</code> contains standard {@link Font}s.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class Fonts {

  public static final Font SMALL  = Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR,  5);                                   
  public static final Font PLAIN  = Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 10);                                   
  public static final Font BOLD   = Font.font("Helvetica", FontWeight.BOLD,   FontPosture.REGULAR, 10);                                   
  public static final Font NONE   = Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR,  0);
  public static final Font HEADER = Font.font("Cabria",    FontWeight.BOLD,   FontPosture.REGULAR, 32);

  }
