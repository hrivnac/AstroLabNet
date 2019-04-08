package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/** <code>SenderEventHandler</code> implements {@link EventHandler} for {@link SenderRep}.
  * It executes {@link Action}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class SenderEventHandler implements EventHandler<ActionEvent> {
  
  /** Create.
    * @param senderRep The associated {@link SenderRep}. */
  public SenderEventHandler(SenderRep senderRep) {
    _senderRep = senderRep;
    }
    
  @Override
  public void handle(ActionEvent event) {
    _senderRep.addTab();
    }
    
  private SenderRep _senderRep;  

  }
