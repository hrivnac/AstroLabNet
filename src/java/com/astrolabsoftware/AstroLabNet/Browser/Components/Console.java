package com.astrolabsoftware.AstroLabNet.Browser.Components;

import com.astrolabsoftware.AstroLabNet.Utils.Info;

// AWT
import java.awt.Color;
import java.awt.Font;

// Swing
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

// Bean Shell
import bsh.util.JConsole;

/** Bean Shell {@link JConsole}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class Console extends JConsole {

  /** TBD */
  public Console() {
    setFont(new Font("Helvetica", Font.PLAIN, 10));
    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    setBorder(BorderFactory.createEtchedBorder());
    print("Welcome to AstroLabNet " + Info.release() + "\n", new Font("Helvetica", Font.BOLD, 12), Color.red);
    print("https://astrolabsoftware.github.io\n", new Font("Helvetica", Font.PLAIN, 10), Color.red);
    }

  }
