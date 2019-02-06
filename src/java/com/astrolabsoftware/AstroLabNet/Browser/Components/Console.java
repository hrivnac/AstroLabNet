package com.astrolabsoftware.AstroLabNet.Browser.Components;

import com.astrolabsoftware.AstroLabNet.Utils.Info;

// AWT
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

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

  public Console() {
    setFont(new Font("Helvetica", Font.PLAIN, 15));
    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    setBorder(BorderFactory.createEtchedBorder());
    setSize(                     Integer.MAX_VALUE, 200);
    setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
    print("Welcome to AstroLabNet " + Info.release() + "\n", new Font("Helvetica", Font.BOLD, 15), Color.red);
    print("https://astrolabsoftware.github.io\n", new Font("Helvetica", Font.PLAIN, 15), Color.red);
    }

  }
