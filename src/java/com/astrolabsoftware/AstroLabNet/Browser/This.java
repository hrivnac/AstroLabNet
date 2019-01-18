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

class This {

  public static void main(String[] args) {
    Init.init();
    new RootFrame();
    }
    
  /** Logging . */
  private static Logger log = Logger.getLogger(RootFrame.class);
      
  }
