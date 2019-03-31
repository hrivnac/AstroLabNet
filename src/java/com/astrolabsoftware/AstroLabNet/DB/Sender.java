package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Livyser.Language;

// Java
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

// Log4J
import org.apache.log4j.Logger;

/** <code>Sender</code> represents <em>Spark</em> sender.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Sender extends Element
                    implements Comparable<Sender> {
  
  /** Create new Sender.
    * @param name     The Sender name.
    * @param server The {@link Server} keeping this Sender. */
  public Sender(String   name,
                Server   server) {
    super(name);
    _server   = server;
    }
    
  @Override
  public int compareTo(Sender sender) {
    return toString().compareTo(sender.toString());
    }
    
  /** Give the keeping {@link Server}.
    * @return The keeping {@link Server}. */
  public Server server() {
    return _server;
    }
    
  /** TBD */
  public void registerBatch(int idBatch) {
    _batchOnSender.put(idBatch, this);
    register();
    }
    
  /** TBD */
  public void register() {
    _senders.add(this);
    }
    
  /** TBD */
  public static Sender sender(int idBatch) {
    return _batchOnSender.get(idBatch);
    }
    
  /** TBD */
  public static Set<Sender> senders() {
    return _senders;
    }
  
  @Override
  public String toString() {
    return name() + " on " + _server.name();
    }
  
  private Server _server;
    
  private static Map<Integer, Sender> _batchOnSender = new HashMap<>();
  
  private static Set<Sender> _senders = new TreeSet<>();
 
  /** Logging . */
  private static Logger log = Logger.getLogger(Sender.class);

  }
