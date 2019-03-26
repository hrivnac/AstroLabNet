package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.scene.control.MenuItem;

// Java
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// Log4J
import org.apache.log4j.Logger;

/** <code>SearchRep</code> is {@link BrowserWindow} representation of {@link Search}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class SearchRep extends ElementRep {
  
  /** Create new SearchRep as a <em>Singleton</em>.
    * @param search  The original {@link Search}.
    * @param browser The {@link BrowserWindow}. */
  // TBD: do factory for other elements too
  public static SearchRep create(Search         search,
                                 BrowserWindow  browser) {
    SearchRep searchRep = _searchReps.get(search.toString());
    if (searchRep == null) {
      searchRep = new SearchRep(search, browser);
      log.info("Adding Search " + searchRep);
      _searchReps.put(search.toString(), searchRep);
      }
    return searchRep;
    }
  
  private static Map<String, SearchRep> _searchReps = new HashMap<>();  
    
  /** Create new SearchRep.
    * @param name    The represented {@link Search}.
    * @param browser The {@link BrowserWindow}. */
  public SearchRep(Search        search,
                   BrowserWindow browser) {
    super(search, browser, Images.SEARCH);
    Thread thread = new Thread() {
      @Override
      public void run() {}
      };
    thread.start();
    }
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem show = new MenuItem("Show Source",  Images.icon(Images.SOURCE));
    show.setOnAction(new SearchEventHandler(this));
    menuItems.add(show);
    return menuItems;
    }
  
  /** Give hosting {@link SourceRep}.
    * @return The hosting {@link SourceRep}. */
  public SourceRep sourceRep() {
    return SourceRep.create(source(), browser());
    }
    
  /** Give the associated hosting {@link Source}.
    * @return The associsted hosting {@link Source}. */
  public Source source() {
    return search().source();
    }
    
  /** Give the referenced {@link Search}.
    * @return The referenced {@link Search}. */
  public Search search() {
    return (Search)element();
    }
    
  @Override
  public String toString() {
    return search().toString();
    }
    
  /** Logging . */
  private static Logger log = Logger.getLogger(SearchRep.class);

  }
