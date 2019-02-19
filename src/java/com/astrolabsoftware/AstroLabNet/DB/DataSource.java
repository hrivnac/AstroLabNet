package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>DataSource</code> represents data source on <em>Spark</em> servers.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class DataSource extends Element {
  
  /** Create new DataSource.
    * @param name The DataSource name. */
  public DataSource(String name) {
    super(name);
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(DataSource.class);

  }
