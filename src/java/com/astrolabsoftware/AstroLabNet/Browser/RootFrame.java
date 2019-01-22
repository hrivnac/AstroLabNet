package com.astrolabsoftware.AstroLabNet.Browser;

import com.astrolabsoftware.AstroLabNet.Livyser.LivyRI;
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
import javax.swing.JTree;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.ToolTipManager;
import javax.swing.JPopupMenu;
import javax.swing.plaf.ColorUIResource;
import javax.swing.AbstractButton;
import javax.swing.tree.DefaultMutableTreeNode;

// Java
import java.io.StringWriter;
import java.io.PrintWriter;

// Bean Shell
import bsh.Interpreter;
import bsh.util.JConsole;
import bsh.EvalError;

// Log4J
import org.apache.log4j.Logger;

/** <code>RootFrame</code> is the root browser for the
  * <em>AstroLabNet</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class RootFrame extends JFrame {

  /** Open and initialise. */
  public RootFrame() {
    super("AstroLabNet Browser");
    // Global Options
    JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
    UIManager.put("ToolTip.font",       new Font("Dialog", Font.PLAIN, 12));
    UIManager.put("ToolTip.foreground", new ColorUIResource(Color.red));
    UIManager.put("ToolTip.background", new ColorUIResource(0.95f, 0.95f, 0.3f));
    
    ActionListener actionListener = new RootActionListener(this);

    LivyRI livy = new LivyRI();
    log.info(livy.sendCommand("xxx"));
    
    // Tools
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

    // Catalog
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
    _catalog = new JTree(root);
    DefaultMutableTreeNode livys = new DefaultMutableTreeNode("Livy Servers");
    DefaultMutableTreeNode livy1 = new DefaultMutableTreeNode("Livy Server 1");
    DefaultMutableTreeNode livy2 = new DefaultMutableTreeNode("Livy Server 2");
    livys.add(livy1);
    livys.add(livy2);
    livy1.add(new DefaultMutableTreeNode("Context 1"));
    livy1.add(new DefaultMutableTreeNode("Context 2"));
    livy2.add(new DefaultMutableTreeNode("Context 1"));
    livy2.add(new DefaultMutableTreeNode("Context 2"));
    DefaultMutableTreeNode datas = new DefaultMutableTreeNode("Data");
    datas.add(new DefaultMutableTreeNode("Dataset 1"));
    datas.add(new DefaultMutableTreeNode("Dataset 2"));
    root.add(livys);
    root.add(datas);
    
    // Results
    _results    = new JTabbedPane();    
    JComponent result1 = new JTextArea("Result 1", 40, 80);
    _results.addTab("Result 1", Icons.ASTROLAB, result1, "Does nothing");
    JComponent result2 = new JTextArea("Result 2", 40, 80);
    _results.addTab("Result 2", Icons.ASTROLAB, result2, "Does twice as much nothing");
 
    // Feedback
    
    // Console
    _console.setWaitFeedback(true);
    //_console.setNameCompletion(...);
    _interpreter = new Interpreter(_console);
    try {
      _interpreter.set("w", this);
      _interpreter.eval("import org.apache.livy.*");
       }
    catch (EvalError e) {
      reportException("Can't evaluate standard BeanShell expression", e, log);
      }

    // Panels
    JSplitPane splitPaneN = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, _catalog,    _results);
    JSplitPane splitPaneS = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, _feedback, _console);
    JSplitPane splitPane  = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPaneN, splitPaneS);
    
    // Packing
    getContentPane().add(north, BorderLayout.NORTH);
    getContentPane().add(splitPane, BorderLayout.CENTER);
    pack();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    _interpreter.run();
    }
    
  /** Add text to {@link JConsole}.
    * @param text The text to be added to  {@link JConsole}. */
  public static void setText(String text) {
    _console.print(text + "\n", new Font("Helvetica", Font.PLAIN, 10), Color.blue);
    }

  /** Add error text to {@link JConsole}.
    * @param text The error text to be added to  {@link JConsole}. */
  public static void setError(String text) {
    _console.print(text + "\n", new Font("Helvetica", Font.PLAIN, 10), Color.red);
    }

  /** Add simple text to {@link JConsole}.
    * @param text The simple text to be added to  {@link JConsole}. */
  public static void setText(String text, Color color) {
    _console.print(text + "\n", new Font("Helvetica", Font.PLAIN, 10), color);
    }

  /** Report {@link Throwable} to the logging system.
    * @param text The text to be reported.
    * @param e    The associated {@link Throwable}.
    * @param l    The {@link Logger} of the origin of the {@link Throwable} . */
  public static void reportException(String text, Throwable e, Logger l) {
    l.error(text + ", see AstroLabNet.log for details");
    StringWriter sw = new StringWriter();
    while (e != null) {
      e.printStackTrace(new PrintWriter(sw));
      e = e.getCause();
      }
    l.debug(sw.toString());
    }

  /** Add feedback.
    * @param text The feedback text to be added. */
  public void appendFeedback(String text) {
    _feedback.append(text);
    }
    
  /** Close. */
  public void close() {
    System.exit(0);
    }  
    
  private static JTextArea _feedback = new JTextArea("Feedback", 2, 20);
  
  private JComponent _catalog;
  
  private JTabbedPane _results;
  
  private static JConsole _console = new JConsole();

  private Interpreter _interpreter;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(RootFrame.class);

  }
