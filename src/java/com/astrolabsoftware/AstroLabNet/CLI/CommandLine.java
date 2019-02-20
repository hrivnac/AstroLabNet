package com.astrolabsoftware.AstroLabNet.CLI;

import com.astrolabsoftware.AstroLabNet.Utils.Info;

// Bean Shell
import bsh.util.JConsole;
import bsh.Interpreter;

// Bean Shell
import bsh.util.JConsole;
import bsh.Interpreter;

// Java
import java.io.InputStreamReader;

// Log4J
import org.apache.log4j.Logger;

/** Simple Command Line.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class CommandLine {

  /** Start {@link Interpreter} and run forever. */
  public CommandLine() {
    Interpreter interpreter = new Interpreter(new InputStreamReader(System.in), System.out, System.err, true);
    interpreter.print("Welcome to AstroLabNet " + Info.release() + "\n");
    interpreter.print("https://astrolabsoftware.github.io\n");
    new Thread(interpreter).start();
    }
 
  /** Logging . */
  private static Logger log = Logger.getLogger(CommandLine.class);
   
  }
    
    