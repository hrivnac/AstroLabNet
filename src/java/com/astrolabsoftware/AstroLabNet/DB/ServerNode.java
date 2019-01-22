package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>ServerNode></code> represents <em>Livy</em> server.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ServerNode extends TreeNode {
  
  /** TBD */
  public ServerNode(String name, String url) {
    super(name);
    _url = url;
    }
  
  /** TBD */
  public String url() {
    return _url;
    }
    
  private String _url;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(ServerNode.class);

  }
