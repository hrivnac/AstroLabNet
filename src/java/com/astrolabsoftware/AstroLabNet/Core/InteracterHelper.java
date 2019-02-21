package com.astrolabsoftware.AstroLabNet.Core;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.Utils.StringFile;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.Init;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Core.Interacter;

// Log4J
import org.apache.log4j.Logger;

/** Default methods for {@link Interacter}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class InteracterHelper {
  
  public static void readActions(Interacter interacter) {
    interacter.addAction("Test", "1+1", Language.PYTHON);
    String ext;
    Language lang = Language.PYTHON;
    for (String actionTxt : new String[] {"pi.py",
                                          "pi.scala"}) {
      try {
        ext = actionTxt.substring(actionTxt.lastIndexOf(".") + 1);
        switch (ext) { // TBD: put into Language
          case "py":
            lang = Language.PYTHON;
            break;
          case "scala":
            lang = Language.SCALA;
            break;
           case "r":
            lang = Language.R;
            break;
          case "sql":
            lang = Language.SQL;
            break;
          default:
            log.error("Unknown extension " + ext + ", supposing Python");
          } 
        interacter.addAction("pi", new StringResource("com/astrolabsoftware/AstroLabNet/DB/Actions/" + actionTxt).toString(), lang);
        }
      catch (AstroLabNetException e) {
        log.error("Cannot load Action from " + actionTxt, e);
        }
      }
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(InteracterHelper.class);
  
  }
