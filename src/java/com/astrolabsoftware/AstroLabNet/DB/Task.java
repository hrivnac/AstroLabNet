package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>Task</code> represents a task running or finished on <em>Spark</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Task extends Element {  
    
  /** Create new Task.
    * Check the progress.
    * @param name    The Task name.
    * @param session The hosting {@link Session}.
    * @param id      The Statement id. */
  public Task(String     name,
              Session    session,
              int        id) {
    super(name);
    _session = session;
    _id      = id;
    }
  
  /** Give hosting {@link Session}.
    * @return The hosting {@link Session}. */
  public Session session() {
    return _session;
    }
    
  /** Give the Statement id.
    * @return The Statement id. */
  public int id() {
    return _id;
    }
     
  @Override
  public String toString() {
    return name();
    }
   
  private Session _session;  
  
  private int _id;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(Task.class);

  }
