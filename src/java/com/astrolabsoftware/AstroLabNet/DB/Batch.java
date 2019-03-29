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
    * @param language The Batch {@link Language}.
    * @param server The {@link Server} keeping this Batch. */
  public Batch(String   name,
               int      id,
               Language language,
               Server   server) {
    super(name);
    _id       = id;
    _language = language;
    _server   = server;
    }
    
  /** Give the Batch id.
    * @return The Batch id. */
  public int id() {
    return _id;
    }
    
  /** Give the Batch {@link Language}.
    * @return The Batch {@link Language}. */
  public Language language() {
    return _language;
    }
    
  /** Give the keeping {@link Server}.
    * @return The keeping {@link Server}. */
  public Server server() {
    return _server;
    }
    
  @Override
  public String toString() {
    return name() + " : " + _id + " on " + _server.name() + " in " + _language;
    }
    
  private int _id;
  
  private Language _language;
  
  private Server _server;
 
  /** Logging . */
  private static Logger log = Logger.getLogger(Batch.class);

  }
