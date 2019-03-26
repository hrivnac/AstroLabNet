package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX

// org.json

// Java

// Log4J
import org.apache.log4j.Logger;

/** <code>Search</code> represents a search running or finished on <em>HBase</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Search extends Element {  
    
  /** Create new Search.
    * @param name   The Search name.
    * @param source The hosting {@link Source}. */
  public Search(String name,
                Source source) {
    super(name);
    _source = source;
    }
  
  /** Give hosting {@link Source}.
    * @return The hosting {@link Source}. */
  public Source source() {
    return _source;
    }
     
  @Override
  public String toString() {
    return name();
    }
   
  private Source _source;  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(Search.class);

  }
