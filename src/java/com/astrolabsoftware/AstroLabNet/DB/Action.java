package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>Action</code> represents <em>Spark</em> job or transfer
  * ready to run.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Action extends Element {
  
  /** TBD */
  public Action(String name) {
    super(name);
    }

  /** TBD */
  public String toString() {
    return name();
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Action.class);

  }
