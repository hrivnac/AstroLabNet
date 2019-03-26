package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/** <code>SourceEventHandler</code> implements {@link EventHandler} for {@link SourceRep}.
  * It executes {@link Action}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class SourceEventHandler implements EventHandler<ActionEvent> {
  
  /** Create.
    * @param sourceRep The associated {@link SourceRep}. */
  public SourceEventHandler(SourceRep sourceRep) {
    _sourceRep = sourceRep;
    }
    
  @Override
  public void handle(ActionEvent event) {
    _sourceRep.addTab();
    }
    
  private SourceRep _sourceRep;  

  }
