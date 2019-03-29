package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.DB.*;

// Log4J
import org.apache.log4j.Logger;

/** <code>Batch</code> represents a batch running or finished on <em>Spark</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Batch extends Element {  
    
  /** Create new Batch.
    * Check the progress.
    * @param name    The Batch name.
    * @param session The hosting {@link Session}.
    * @param id      The statement id. */
  public Batch(String     name,
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
    
  /** Give the hosting {@link Session} id.
    * @return The hosting {@link Session} id. */
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
  private static Logger log = Logger.getLogger(Batch.class);

  }
