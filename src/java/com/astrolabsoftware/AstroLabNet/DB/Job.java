package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>Job</code> represents running <em>Spark</em> job
  * transfer.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Job extends Element {
  
  /** Create new Job.
    * @param name The Job name. */
  public Job(String name) {
    super(name);
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Job.class);

  }
