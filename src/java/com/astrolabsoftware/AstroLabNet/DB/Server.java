package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

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
                String urlLivy,
                String urlSpark) {
    super(name);
    _urlLivy   = urlLivy;
    _urlSpark  = urlSpark;
    }
        
  public String urlLivy() {
    return _urlLivy;
    }
        
  public String urlSpark() {
    return _urlSpark;
    }
  
  public String toString() {
    return name() + " (Livy = " + _urlLivy + ", Spark = " + _urlSpark + ")";
    }
    
  private String _urlLivy;
  
  private String _urlSpark;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Server.class);

  }
