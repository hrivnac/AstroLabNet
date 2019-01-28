package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>Channel</code> represents {@link Data} channel.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Channel extends Element {
  
  /** TBD */
  public Channel(String name) {
    super(name);
    }

  /** TBD */
  public String toString() {
    return name();
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Channel.class);

  }
