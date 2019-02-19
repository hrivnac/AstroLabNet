package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>Channel</code> represents data channel between <em>Spark</em> servers.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
// TBD: rename to DataChannel
public class Channel extends Element {

  /** Create new Channel.
    * @param name The Channel name. */
  public Channel(String name) {
    super(name);
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Channel.class);

  }
