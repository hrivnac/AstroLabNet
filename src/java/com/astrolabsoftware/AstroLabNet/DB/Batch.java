package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Livyser.Language;

// Log4J
import org.apache.log4j.Logger;

/** <code>Batch</code> represents <em>Spark</em> batch.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Batch extends Element {
  
  /** Create new Batch.
    * @param name     The Batch name.
    * @param id       The Batch id.
    * @param server The {@link Server} keeping this Batch. */
  public Batch(String   name,
               int      id,
               Server   server) {
    super(name);
    _id       = id;
    _server   = server;
    }
    
  /** Give the Batch id.
    * @return The Batch id. */
  public int id() {
    return _id;
    }
    
  /** Give the keeping {@link Server}.
    * @return The keeping {@link Server}. */
  public Server server() {
    return _server;
    }
    
  @Override
  public String toString() {
    return name() + " : " + _id + " on " + _server.name();
    }
    
  private int _id;
  
  private Server _server;
 
  /** Logging . */
  private static Logger log = Logger.getLogger(Batch.class);

  }
