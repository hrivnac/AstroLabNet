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
    * @param server The {@link Server} keeping this Batch. */
  public Batch(String   name,
               Server   server) {
    super(name);
    _server   = server;
    }
    
  /** Give the keeping {@link Server}.
    * @return The keeping {@link Server}. */
  public Server server() {
    return _server;
    }
    
  /** Attribute the id.
    * @param id The new id. */
  public void setId(int id) {
    _id = id;
    }
    
  /** Give the attributed id.
    * @return The attributed id.
    *         If <tt>0</tt>, no id attributed yet. */
  public int id() {
    return _id;
    }
    
  @Override
  public String toString() {
    return name() + " on " + _server.name();
    }
  
  private Server _server;
  
  private int _id;
 
  /** Logging . */
  private static Logger log = Logger.getLogger(Batch.class);

  }
