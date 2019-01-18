package com.astrolabsoftware.AstroLabNet.Browser;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// AWT
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.event.ActionListener;

// Swing
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JSplitPane;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.ToolTipManager;
import javax.swing.JPopupMenu;
import javax.swing.plaf.ColorUIResource;
import javax.swing.AbstractButton;

// Log4J
import org.apache.log4j.Logger;

class RootFrame extends JFrame {

  public RootFrame() {
    super("AstroLabNet Browser");
    // Global Options
    JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
    UIManager.put("ToolTip.font",       new Font("Dialog", Font.PLAIN, 12));
    UIManager.put("ToolTip.foreground", new ColorUIResource(Color.red));
    UIManager.put("ToolTip.background", new ColorUIResource(0.95f, 0.95f, 0.3f));
    
    ActionListener actionListener = new RootActionListener(this);

    JToolBar north = new JToolBar();
    north.add(new AboutLabel());
    north.add(new SimpleButton("Exit",
                               Icons.EXIT,
                               AbstractButton.CENTER,
                               Fonts.NONE,
                               Dimensions.BIG,
                               "Exit",
                               actionListener));
    north.setFloatable(false);
    north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
    getContentPane().add(north, BorderLayout.NORTH);

    _leftTop     = new JTextArea("Catalog", 21, 40);
    _leftBottom  = new JTextArea("Feedback", 1, 40);
    _rightTop    = new JTextArea("Result",  11, 80);
    _rightBottom = new JTextArea("Command", 11, 80);
    JSplitPane splitPaneN = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, _leftTop,    _rightTop);
    JSplitPane splitPaneS = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, _leftBottom, _rightBottom);
    JSplitPane splitPane  = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPaneN, splitPaneS);
    getContentPane().add(splitPane, BorderLayout.CENTER);


    pack();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    }
    
  public void close() {
    System.exit(0);
    }

  private JComponent _leftTop;
  private JComponent _leftBottom;
  private JComponent _rightTop;
  private JComponent _rightBottom;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(RootFrame.class);

  }
