package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;

// Log4J
import org.apache.log4j.Logger;

/** <code>DataRep</code>  is {@link BrowserWindow} representation of {@link Data}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class DataRep extends ElementRep {
  
  /** Create new DataRep.
    * @param name    The Data nameRep. 
    * @param browser The {@link BrowserWindow}. */
  public DataRep(String        name, 
                 BrowserWindow browser) {
    super(name, browser);
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(DataRep.class);

  }
