package com.astrolabsoftware.AstroLabNet.Browser;

import com.astrolabsoftware.AstroLabNet.Utils.Init;

// JavaFX
import javafx.application.Application;

/** The <em>AstroLabNet</em> browser entry point.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class This {
  
  /** Initialise and open the browser. */
  public static void main(String[] args) {
    Init.init(args);
    if (Init.asCLI()) {
      }
    else if (Init.asBrowser()) {
      Application.launch(BrowserWindow.class, args);
      }
    }
  
  }