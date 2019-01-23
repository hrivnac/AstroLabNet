package com.astrolabsoftware.AstroLabNet.Browser.Actions;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;

// JavaFX
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

// Log4J
import org.apache.log4j.Logger;

/** <code>ExitHandler</code> handles <em>Exit</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class ExitHandler implements EventHandler<ActionEvent> {

  public ExitHandler(BrowserWindow w) {
    super();
    _w = w;
    }
  @Override
  public void handle(ActionEvent event) {
    _w.close();
    }
    
  private BrowserWindow _w;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(ExitHandler.class);

  }
