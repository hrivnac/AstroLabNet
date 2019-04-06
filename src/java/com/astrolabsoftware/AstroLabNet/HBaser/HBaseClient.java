package com.astrolabsoftware.AstroLabNet.HBaser;

// Log4J
import org.apache.log4j.Logger;

/** <code>HBaseCLient</code> is the bridge to the <em>HBase</em> REST service
  * talking directly to TBD
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class HBaseClient extends HBaseRESTClient {
  
  /** Connect to the server.
    * @param url The url of the server. */
  public HBaseClient(String url) {
    super(url);
    }

  @Override
  public String toString() {
    return "HBaseClient of " + super.toString();
    }

  /** Logging . */
  private static Logger log = Logger.getLogger(HBaseClient.class);

  }
