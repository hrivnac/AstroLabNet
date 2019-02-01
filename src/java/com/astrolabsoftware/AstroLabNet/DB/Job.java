package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;

// Log4J
import org.apache.log4j.Logger;

/** <code>Job</code> represents running <em>Spark</em> job
  * transfer.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Job extends Element {
  
  /** Create new Job.
    * @param name    The Job name.
    * @param browser The {@link BrowserWindow}. */
  public Job(String        name,
             BrowserWindow browser) {
    super(name, browser);
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Job.class);

  }
