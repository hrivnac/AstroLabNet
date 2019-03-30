package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/** <code>BatchEventHandler</code> implements {@link EventHandler} for {@link BatchRep}.
  * It executes {@link Action}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class BatchEventHandler implements EventHandler<ActionEvent> {
  
  /** Create.
    * @param batchRep The associated {@link BatchRep}. */
  public BatchEventHandler(BatchRep batchRep) {
    _batchRep = batchRep;
    }
    
  @Override
  public void handle(ActionEvent event) {
    //_taskRep.browser().selectTab(_batchRep.jobRep());
    }
    
  private BatchRep _batchRep;  

  }
