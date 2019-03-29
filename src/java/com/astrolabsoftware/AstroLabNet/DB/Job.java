package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Livyser.Language;

// Log4J
import org.apache.log4j.Logger;

/** <code>Job</code> represents an action to be executed on <em>Spark</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Job extends Element {  
  
  /** Create new Job.
    * @param name     The Job name.
    * @param file      The jar filename.
    * @param className The <em>main</em> className. */
  public Job(String        name,
             String        file,
             String        className) {
    super(name);
    _file      = file;
    _className = className;
    }

  /** Give the Job jar filename.
    * @return The Job jar filename. */
  public String file() {
    return _file;
    }
    
  /** Give the Job <em>main</em> className.
    * @return The <em>main</em> className. */
  public String className() {
    return _className;
    }
    
  @Override
  public String toString() {
    return name() + " (" + _className + "  from " + _file + ")";
    }

  private String _file;
  
  private String _className;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Job.class);

  }
