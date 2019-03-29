package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

// Log4J
import org.apache.log4j.Logger;

/** <code>BatchEventHandler</code> implements {@link EventHandler} for {@link BatchRep}.
  * It shows related {@link Session}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class BatchEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param batchRep The {@link BatchRep} to run on. */
  public BatchEventHandler(BatchRep batchRep) {
    _batchRep = batchRep;
    }
 
  @Override
  public void handle(ActionEvent event) {
    _batchRep.browser().selectTab(_batchRep.sessionRep());
    }
 
  private BatchRep _batchRep;  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(BatchEventHandler.class);
    
  }
