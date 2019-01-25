package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>Server</code> represents <em>Livy</em> server.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Server extends Element {
  
  public Server(String name,
                String url) {
    super(name);
    _url  = url;
    }
        
  public String url() {
    return _url;
    }
  
  public String toString() {
    return name() + " : " + _url;
    }
    
  private String _url;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Server.class);

  }
