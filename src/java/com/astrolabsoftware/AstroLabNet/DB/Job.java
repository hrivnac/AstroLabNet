package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>JOib</code> represents <em>Spark</em> job.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Job extends Element {
  
  /** TBD */
  public Job(String name) {
    super(name);
    }

  /** TBD */
  public String toString() {
    return name();
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Job.class);

  }
