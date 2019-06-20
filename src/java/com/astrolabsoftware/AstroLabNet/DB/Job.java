package com.astrolabsoftware.AstroLabNet.DB;

// Java
import java.io.File;
import java.io.IOException;

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
    * @param name           The Job name.
    * @param file           The jar or py filename.
    * @param className      The <em>main</em> className for jar file,
    *                       <tt>null</tt> for py file.
    * @param args           The Job args, if any. 
    * @param driverMemory   The Job driver memory or <tt>null</tt>. 
    * @param driverCores    The Job driver cores or <tt>0</tt>.  
    * @param executorMemory The Job executor memory or <tt>null</tt>. 
    * @param executorCores  The Job executor cores or <tt>0</tt>. */
  public Job(String name,
             String file,
             String className,
             String args,
             String driverMemory,
             int    driverCores,
             String executorMemory,
             int    executorCores) {
    super(name);
    _file = file;
    _className      = className;
    if (args != null) {
      _args           = args;
      }
    if (driverMemory != null) {
      _driverMemory   = driverMemory;
      }
    if (driverCores != 0) {
      _driverCores    = driverCores;
      }
    if (executorMemory != null) {
      _executorMemory = executorMemory;
      }
    if (executorCores != 0) {
      _executorCores  = executorCores;
      }
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
    
  /** Give the Job args.
    * @return The Job args, if any. */
  public String args() {
    return _args;
    }
    
  /** Give the Job driver memory.
    * @return The Job driver memory, can be <tt>null</tt>. */
  public String driverMemory() {
    return _driverMemory;
    }
    
  /** Give the Job driver cores.
    * @return The Job driver cores, can be <tt>0</tt>. */
  public int driverCores() {
    return _driverCores;
    }
    
  /** Give the Job executor memory.
    * @return The Job executor memory, can be <tt>null</tt>. */
  public String executorMemory() {
    return _executorMemory;
    }
    
  /** Give the Job executor cores.
    * @return The Job executor cores, can be <tt>0</tt>. */
  public int executorCores() {
    return _executorCores;
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
    return name() + " (" + _className + "  from " + _file + ", using " + _args + ")";
    }
    
  private String  _file;
  
  private String  _className;
  
  private String  _args = "";
  
  private String  _driverMemory = "1g";
  
  private int     _driverCores = 3;
  
  private String  _executorMemory = "1g";
  
  private int     _executorCores = 3;
   
  private boolean _new = false;
 
  /** Logging . */
  private static Logger log = Logger.getLogger(Job.class);

  }
