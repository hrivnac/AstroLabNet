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
  
  /** Create new Element.
    * @param name The Element name. */
  public Element(String name) {
    _name = name;
    }
    
  /** Give the Element name.
    * @return The Element name. */
  public String name() {
    return _name;
    }
  
  /** Execute an operation associated with this Element.
    * It can require additional data.
    * The default operation does nothing. */
  public void use() {
    log.error("Nothing can be done !");
    }
    
  @Override
  public String toString() {
    return _name;
    }
          
  private String _name;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Element.class);

  }
