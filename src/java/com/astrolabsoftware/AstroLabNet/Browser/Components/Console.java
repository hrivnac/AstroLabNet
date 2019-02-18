package com.astrolabsoftware.AstroLabNet.Browser.Components;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Utils.Info;
import com.astrolabsoftware.AstroLabNet.Utils.Init;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;
import com.astrolabsoftware.AstroLabNet.Utils.StringFile;

// AWT
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

// Swing
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

// Bean Shell
import bsh.util.JConsole;
import bsh.Interpreter;
import bsh.util.JConsole;
import bsh.EvalError;

// Java
import java.io.InputStreamReader;

// Log4J
import org.apache.log4j.Logger;

/** Bean Shell {@link JConsole}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class Console extends JConsole {

  /** Create.
    * @param browser The hosting {@link BrowserWindow}.
    * @param graphical Whether {@link BrowserWindow} is visible. */
  public Console(BrowserWindow browser,
                 boolean       graphical) {
    _browser = browser;
    setFont(new Font("Helvetica", Font.PLAIN, 15));
    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    setBorder(BorderFactory.createEtchedBorder());
    setSize(                     Integer.MAX_VALUE, 200);
    setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
    setMinimumSize(new Dimension(200,               200));
    setWaitFeedback(true);
    if (graphical) {
      _interpreter = new Interpreter(this);
      }
    else {
     _interpreter = new Interpreter(new InputStreamReader(System.in), System.out, System.err, true);
     }
    print("Welcome to AstroLabNet " + Info.release() + "\n", new Font("Helvetica", Font.BOLD, 15), Color.red);
    print("https://astrolabsoftware.github.io\n", new Font("Helvetica", Font.PLAIN, 15), Color.red);
    Thread t  = new Thread(_interpreter);
    t.start();
    _this = this;
    }    
    
  /** Load standard init files and setup standard environment. */
  public void init() {
    String init = "";
    if (Init.source() != null) {
      log.info("Sourcing " + Init.source());
      try {
        init += new StringFile(Init.source()).toString();
        }
      catch (AstroLabNetException e) {
        log.warn(Init.source() + " file cannot be read, the default setup with Local Host server is used.");
        log.debug(Init.source() + " file cannot be read, the default setup with Local Host server is used.", e);
        }
      }
    log.info("Sourcing init.bsh");
    try {
      init += new StringFile("init.bsh").toString();
      }
    catch (AstroLabNetException e) {
      log.warn("init.bsh file cannot be read, the default setup with Local Host server is used.");
      log.debug("init.bsh file cannot be read, the default setup with Local Host server is used.", e);
      }
    if (init.equals("")) {
      log.warn("no suitable init bsh file found, the default setup with Local Host server will be used.");
      init = "w.addServer(\"Local Host\", \"http://localhost:8998\", \"http://localhost:4040\")";
      }
    try {
      _interpreter.set("w", _browser);
      _interpreter.eval("import com.astrolabsoftware.AstroLabNet.DB.*");
      _interpreter.eval(init);
      }
    catch (EvalError e) {
      log.error("Can't evaluate standard BeanShell expression", e);
      }
    }
    
  /** Add text to {@link JConsole}.
    * Write to <em>stdout</em> if {@link JConsole} not yet initialised.
    * @param text The text to be added to {@link JConsole}. */
  public static void setText(String text) {
    if (_this == null) {
      //System.out.println(text);
      }
    else {
      _this.print(text + "\n", new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 20), java.awt.Color.blue);
      }
    }
    
  /** Add error text to {@link JConsole}.
    * Write to <em>stderr</em> if {@link JConsole} not yet initialised.
    * @param text The error text to be added to  {@link JConsole}. */
  public static void setError(String text) {
    if (_this == null) {
      //System.err.println(text);
      }
    else {
      _this.print(text + "\n", new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 20), java.awt.Color.red);
      }
    }
    
  private Interpreter _interpreter;  
  
  private BrowserWindow _browser;
  
  private static Console _this;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(Console.class);

  }
