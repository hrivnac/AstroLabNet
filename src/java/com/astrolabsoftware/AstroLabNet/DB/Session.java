package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>Session</code> represents <em>Livy</em> session.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Session extends Element {
  
  /** TBD */
  public Session(String name,
                 int id) {
    super(name);
    _id = id;
    }
    
  /** TBD */
  public int id() {
    return _id;
    }

  /** TBD */
  public String toString() {
    return name() + " : " + _id;
    }
    
  private int _id;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Session.class);

  }
