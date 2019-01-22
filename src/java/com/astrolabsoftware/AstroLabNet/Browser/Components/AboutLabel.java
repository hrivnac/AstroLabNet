package com.astrolabsoftware.AstroLabNet.Browser.Components;

// Swing
import javax.swing.JLabel;

public final class AboutLabel extends JLabel {

/** <code>AboutLabel</code> of <em>AstroLabNet</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
  public AboutLabel() {
    super("");
    setIcon(Icons.ASTROLAB);
    setToolTipText("<html><h1> AstroLabNet </h1>"
                 + "<h2>Frond end for the AstroLabNet</h2>"
                 + "<h3>https://astrolabsoftware.github.io/</h3></html>");
    }

  }
