package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.Browser.Reps.SearchRep;

// JavaFX
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

// Log4J
import org.apache.log4j.Logger;

/** <code>SearchEventHandler</code> implements {@link EventHandler} for {@link SearchRep}.
  * It shows related {@link Source}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class SearchEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param searchRep The {@link SearchRep} to run on. */
  public SearchEventHandler(SearchRep searchRep) {
    _searchRep = searchRep;
    }
 
  @Override
  public void handle(ActionEvent event) {
    _searchRep.browser().selectTab(_searchRep.sourceRep());
    }
 
  private SearchRep _searchRep;  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(SearchEventHandler.class);
    
  }
