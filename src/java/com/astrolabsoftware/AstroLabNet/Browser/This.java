package com.astrolabsoftware.AstroLabNet.Browser;

import com.astrolabsoftware.AstroLabNet.Utils.Init;

// AWT
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Swing
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

// Log4J
import org.apache.log4j.Logger;

/** <code>This</code> starts <em>AstroLabNet</code> client browser.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
class This {

  /** Initialise and start client browser.*/
  public static void main(String[] args) {
    Init.init();
    new RootFrame();
    }
    
  /** Logging . */
  private static Logger log = Logger.getLogger(RootFrame.class);
      
  }
