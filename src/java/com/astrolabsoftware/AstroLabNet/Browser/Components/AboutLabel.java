package com.astrolabsoftware.AstroLabNet.Browser.Components;

// Swing
import javax.swing.JLabel;

public final class AboutLabel extends JLabel {

  public AboutLabel() {
    super("");
    setIcon(Icons.ASTROLAB);
    setToolTipText("<html><h1> AstroLabNet </h1>"
                 + "<h2>TBD</h2>"
                 + "<h3>https://astrolabsoftware.github.io/</h3></html>");
    }

  }
