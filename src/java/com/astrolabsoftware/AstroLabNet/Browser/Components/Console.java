package com.astrolabsoftware.AstroLabNet.Browser.Components;

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

// Log4J
import org.apache.log4j.Logger;

/** Bean Shell {@link JConsole}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class Console extends JConsole {

  /** Create. TBD */
  public Console(String name,
                 String url,
                 String release) {
    _this = this;
    setFont(new Font("Helvetica", Font.PLAIN, 15));
    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    setBorder(BorderFactory.createEtchedBorder());
    setSize(                     Integer.MAX_VALUE, 200);
    setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
    setMinimumSize(new Dimension(200,               200));
    setWaitFeedback(true);
    _interpreter = new Interpreter(this);
    print("Welcome to " + name + " " + release + "\n", new Font("Helvetica", Font.BOLD, 15), Color.red);
    print(url + "\n", new Font("Helvetica", Font.PLAIN, 15), Color.red);
    new Thread(_interpreter).start();
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
    
  /** Give embedded {@link Interpreter}.
    * @return The embedded {@link Interpreter}. */
  public Interpreter interpreter() {
    return _interpreter;
    }
    
  private Interpreter _interpreter;  
  
  private static Console _this;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(Console.class);

  }
