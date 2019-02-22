package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

// Java

// Log4J
import org.apache.log4j.Logger;

/** <code>ActionsEventHandler</code> implements {@link EventHandler} for group of {@link ActionRep}s.
  * It fills existing {@link SessionRep}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ActionsEventHandler implements EventHandler<ActionEvent> {

  /** Create. */
  public ActionsEventHandler() {
    }
 
  @Override
  public void handle(ActionEvent event) {
    log.info("Create");
    }
    
  /** Logging . */
  private static Logger log = Logger.getLogger(ActionsEventHandler.class);
    
  }
