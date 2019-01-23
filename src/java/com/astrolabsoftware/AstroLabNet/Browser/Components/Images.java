package com.astrolabsoftware.AstroLabNet.Browser.Components;

// Swing
import javafx.scene.image.Image;

/** <code>Icons</code> contains standard {@link Icon}s.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class Images {
  
  public static final ClassLoader myLoader = new Images().getClass().getClassLoader();

  public static final Image ASTROLAB = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/AstroLab.png"));

  public static final Image EXIT     = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/exit.gif"));

  }
