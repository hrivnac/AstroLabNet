package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>DataChannel</code> represents data channel between <em>Spark</em> servers.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class DataChannel extends Element {

  /** Create new DataChannel.
    * @param name The DataChannel name. */
  public DataChannel(String name) {
    super(name);
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(DataChannel.class);

  }
