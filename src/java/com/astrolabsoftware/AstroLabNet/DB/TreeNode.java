package com.astrolabsoftware.AstroLabNet.DB;

// Swing
import javax.swing.tree.DefaultMutableTreeNode;

// Log4J
import org.apache.log4j.Logger;

/** <code>TreeNode></code> is generic database object.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class TreeNode extends DefaultMutableTreeNode {

  /** TBD */
  public TreeNode(String name) {
    super(name);
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(TreeNode.class);

  }
