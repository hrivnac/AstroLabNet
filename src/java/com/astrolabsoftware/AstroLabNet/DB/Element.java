package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>Element></code> is generic database object.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Element {
  
  /** TBD */
  public Element(String name) {
    _name = name;
    }
    
  /** TBD */
  public String name() {
    return _name;
    }
  
  /** TBD */
  public String toString() {
    return _name;
    }
    
  private String _name;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Element.class);

  }
