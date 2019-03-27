package com.astrolabsoftware.AstroLabNet.CLI;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.Utils.Info;
import com.astrolabsoftware.AstroLabNet.Core.DefaultInteracter;

// Bean Shell
import bsh.Interpreter;

// Java
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.PushbackReader;
import java.nio.charset.StandardCharsets;

// Log4J
import org.apache.log4j.Logger;

/** Simple Command Line.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class CommandLine extends DefaultInteracter {

  /** Does nothing. */
  public CommandLine() {}

  /** Start {@link Interpreter} and run forever.
    * @param args The command arguments. Ignored. */
  public CommandLine(String[] args) {
    Interpreter interpreter = new Interpreter(new InputStreamReader(System.in), System.out, System.err, true);
    interpreter.print("Welcome to AstroLabNet " + Info.release() + "\n");
    interpreter.print("https://astrolabsoftware.github.io\n");
    setupInterpreter(interpreter);
    new Thread(interpreter).start();
    }
 
  /** Logging . */
  private static Logger log = Logger.getLogger(CommandLine.class);
   
  }
    
    