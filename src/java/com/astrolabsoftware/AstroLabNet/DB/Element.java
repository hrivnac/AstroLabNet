package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// Log4J
import org.apache.log4j.Logger;

/** <code>Element></code> is generic database object.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Element {
  
  public Element(String name) {
    _name = name;
    }
    
  public String name() {
    return _name;
    }
  
  public String toString() {
    return _name;
    }
          
  private String _name;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Element.class);

  }
