package com.astrolabsoftware.AstroLabNet.Browser.Components;

// Swing
import javax.swing.ImageIcon;

public final class Icons {
  
  private static final ClassLoader myLoader = (new Icons()).getClass().getClassLoader();

  public static final ImageIcon ASTROLAB = new ImageIcon(myLoader.getResource("com/astrolabsoftware/AstroLabNet/Browser/Components/images/AstroLab.png"));

  public static final ImageIcon EXIT     = new ImageIcon(myLoader.getResource("com/astrolabsoftware/AstroLabNet/Browser/Components/images/exit.gif"));

  }
