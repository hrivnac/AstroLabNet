package com.astrolabsoftware.AstroLabNet.DB;

// Log4J
import org.apache.log4j.Logger;

/** <code>SessionNode></code> represents <em>Livy</em> session.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class SessionNode extends TreeNode {
  
  /** TBD */
  public SessionNode(String name, int id) {
    super(name);
    _id = id;
    }
  
  /** TBD */
  public SessionNode(int id) {
    super("Session " + new Integer(id).toString());
    _id = id;
    }
  
  /** TBD */
  public int id() {
    return _id;
    }
    
  private int _id;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(SessionNode.class);

  }
