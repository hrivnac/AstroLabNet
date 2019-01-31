package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>Data</code> represents <em>Spark</em> data.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Data extends Element {
  
  /** Create new Data.
    * @param name The Data name. */
  public Data(String name) {
    super(name);
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Data.class);

  }
