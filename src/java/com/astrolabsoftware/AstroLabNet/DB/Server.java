package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.HBaser.HBaseRESTClient;
import com.astrolabsoftware.AstroLabNet.Livyser.LivyRESTClient;

// Log4J
import org.apache.log4j.Logger;

/** <code>Server</code> represents <em>Spark</em> server behind <em>Livy</em> proxy.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Server extends Element {
  
  /** Create new Spark and Livy Server.
    * @param name     The Server name.
    * @param urlLivy  The url of the Spark Server Livy interface.
    * @param urlSpark The url of the Spark Server.
    * @param urlHBase The url of the HBase Server. */
  public Server(String        name,
                String        urlLivy,
                String        urlSpark,
                String        urlHBase) {
    super(name);
    setLivy(urlLivy);
    setSpark(urlSpark);
    setHBase(urlHBase);
    }
        
  /** Create new Spark and Livy Server.
    * @param name The Server name. */
  public Server(String name) {
    super(name);
    }
    
  /** Set Spark Server Livy interface.
    * @param urlLivy  The url of the Spark Server Livy interface. */
  public void setLivy(String url) {
    _urlLivy = url;
    _livy = new LivyRESTClient(url);
    }

  /** Set Spark Server.
    * @param urlLivy  The url of the Spark Server. */
  public void setSpark(String urlSpark) {
    _urlSpark = urlSpark;
    }
 
  /** Set HBase Server.
    * @param url The url of the HBase Server. */
  public void setHBase(String url) {
    _urlHBase = url;
    _hbase    = new HBaseRESTClient(url);
    }
    
  /** Give Spark Server Livy interface url.
    * @return The Spark Server Livy interface url. */
  public String urlLivy() {
    return _urlLivy;
    }
        
  /** Give Spark Server url.
    * @return The Spark Server url. */
  public String urlSpark() {
    return _urlSpark;
    }  

  /** Give HBase Server url.
    * @return The HBase Server url. */
  public String urlHBase() {
    return _urlHBase;
    }  
    
  /** Give {@link LivyRESTClient}.
    * @return The {@link LivyRESTClient}. */
  public LivyRESTClient livy() {
    return _livy;
    }
    
  /** Give {@link HBaseRESTClient}.
    * @return The {@link HBaseRESTClient}. */
  public HBaseRESTClient hbase() {
    return _hbase;
    }
    
  @Override
  public String toString() {
    return name() + " (Livy = " + _urlLivy + ", Spark = " + _urlSpark + ", HBase = " + _urlHBase + ")";
    }
    
  private String _urlLivy;
  
  private String _urlSpark;
  
  private String _urlHBase;
  
  private LivyRESTClient _livy;
  
  private HBaseRESTClient _hbase;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Server.class);

  }

