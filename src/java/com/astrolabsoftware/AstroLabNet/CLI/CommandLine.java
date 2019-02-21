package com.astrolabsoftware.AstroLabNet.CLI;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.Utils.StringFile;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.Init;
import com.astrolabsoftware.AstroLabNet.Utils.Info;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Core.Interacter;
import com.astrolabsoftware.AstroLabNet.Core.DefaultInteracter;

// Bean Shell
import bsh.util.JConsole;
import bsh.Interpreter;
import bsh.EvalError;

// Java
import java.util.List;
import java.util.ArrayList;
import java.io.InputStreamReader;

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
    readActions();
    }
 
  /** Logging . */
  private static Logger log = Logger.getLogger(CommandLine.class);
   
  }
    
    