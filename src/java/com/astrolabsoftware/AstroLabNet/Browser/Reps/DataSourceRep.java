package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;

// Log4J
import org.apache.log4j.Logger;

/** <code>DataSourceRep</code> is {@link BrowserWindow} representation of {@link DataSource}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class DataSourceRep extends ElementRep {
  
  /** Create new DataSourceRep.
    * @param name    The DataSourceRep name.
    * @param browser The {@link BrowserWindow}. */
  public DataSourceRep(String        name,
                       BrowserWindow browser) {
    super(name, browser);
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(DataSourceRep.class);

  }
