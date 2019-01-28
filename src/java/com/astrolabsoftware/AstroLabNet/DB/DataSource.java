package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>DataSource</code> represents {@link Data} source.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class DataSource extends Element {
  
  /** TBD */
  public DataSource(String name) {
    super(name);
    }

  /** TBD */
  public String toString() {
    return name();
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(DataSource.class);

  }
