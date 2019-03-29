package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.DB.*;

// Log4J
import org.apache.log4j.Logger;

/** <code>Mission</code> represents a mission running or finished on <em>Spark</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Mission extends Element {  
    
  /** Create new Mission.
    * Check the progress.
    * @param name    The Mission name.
    * @param batch   The hosting {@link Batch}.
    * @param id      The statement id. */
  public Mission(String     name,
                 Batch      batch,
                 int        id) {
    super(name);
    _batch   = batch;
    _id      = id;
    }
  
  /** Give hosting {@link Batch}.
    * @return The hosting {@link Batch}. */
  public Batch batch() {
    return _batch;
    }
    
  /** Give the hosting {@link Batch} id.
    * @return The hosting {@link Batch} id. */
  public int id() {
    return _id;
    }
     
  @Override
  public String toString() {
    return name();
    }
   
  private Batch _batch;  
  
  private int _id;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(Mission.class);

  }
