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
    * @param cmd      The command.
    * @param language The {@link Language} od the command. */
  public Job(String        name,
             String        cmd,
             Language      language) {
    super(name);
    _cmd      = cmd;
    _language = language;
    }

  /** Give the associated command text.
    * @return The associated command text. */
  public String cmd() {
    return _cmd;
    }
    
  /** Give the Job {@link Language}.
    * @return The Job {@link Language}. */
  public Language language() {
    return _language;
    }
    
  @Override
  public String toString() {
    return name() + " (" + _language + ")";
    }

  private String _cmd;
  
  private Language _language;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Job.class);

  }
