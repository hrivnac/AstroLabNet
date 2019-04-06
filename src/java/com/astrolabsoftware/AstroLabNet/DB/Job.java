package com.astrolabsoftware.AstroLabNet.DB;

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
    * @param name      The Job name.
    * @param file      The jar or py filename.
    * @param className The <em>main</em> className for jar file,
    *                  <tt>null</tt> for py file. */
  public Job(String        name,
             String        file,
             String        className) {
    super(name);
    _file      = file;
    _className = className;
    }

  /** Give the Job jar or py filename.
    * @return The Job or py jar filename. */
  public String file() {
    return _file;
    }
    
  /** Give the Job <em>main</em> className or <tt>null</tt> for py file.
    * @return The <em>main</em> className or <tt>null</tt> for py file. */
  public String className() {
    return _className;
    }
    
  /** Set as a new Job, so it will be stored on Exit. */
  public void setNew() {
    _new = true;
    }
    
  /** Whether is new, to be stored on Exit.
    * return Whether is new. */
  public boolean isNew() {
    return _new;
    }
    
  @Override
  public String toString() {
    return name() + " (" + _className + "  from " + _file + ")";
    }

  private String _file;
  
  private String _className;
   
  private boolean _new = false;
 
  /** Logging . */
  private static Logger log = Logger.getLogger(Job.class);

  }
