package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;

// Log4J
import org.apache.log4j.Logger;

/** <code>DataSource</code> represents {@link Data} source.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class DataSource extends Element {
  
  /** Create new DataSource.
    * @param name    The DataSource name.
    * @param browser The {@link BrowserWindow}. */
  public DataSource(String        name,
                    BrowserWindow browser) {
    super(name, browser);
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(DataSource.class);

  }
