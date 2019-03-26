package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>Source</code> represents data source on <em>Spark</em> servers.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Source extends Element {
  
  /** Create new Source.
    * @param name The Source name. */
  public Source(String name) {
    super(name);
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Source.class);

  }
