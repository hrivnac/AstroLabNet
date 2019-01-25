package com.astrolabsoftware.AstroLabNet.Browser;

// JavaFX
import javafx.application.Application;

/** The <em>AstroLabNet</em> browser entry point.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class This {
  
  /** Open the browser. */
  public static void main(String[] args) {
    Application.launch(BrowserWindow.class, args);
    }
  
  }