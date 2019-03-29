package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

// Log4J
import org.apache.log4j.Logger;

/** <code>MissionEventHandler</code> implements {@link EventHandler} for {@link MissionRep}.
  * It shows related {@link Session}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class MissionEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param missionRep The {@link MissionRep} to run on. */
  public MissionEventHandler(MissionRep missionRep) {
    _missionRep = missionRep;
    }
 
  @Override
  public void handle(ActionEvent event) {
    _missionRep.browser().selectTab(_missionRep.sessionRep());
    }
 
  private MissionRep _missionRep;  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(MissionEventHandler.class);
    
  }
