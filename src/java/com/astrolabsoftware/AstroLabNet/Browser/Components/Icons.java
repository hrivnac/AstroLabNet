package com.astrolabsoftware.AstroLabNet.Browser.Components;

// Swing
import javax.swing.ImageIcon;

/** <code>Icons</code> contains standard {@link Icon}s.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class Icons {
  
  private static final ClassLoader myLoader = (new Icons()).getClass().getClassLoader();

  public static final ImageIcon ASTROLAB = new ImageIcon(myLoader.getResource("com/astrolabsoftware/AstroLabNet/Browser/Components/images/AstroLab.png"));

  public static final ImageIcon EXIT     = new ImageIcon(myLoader.getResource("com/astrolabsoftware/AstroLabNet/Browser/Components/images/exit.gif"));

  }
