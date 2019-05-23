package com.astrolabsoftware.AstroLabNet.Graph;

// org.json
import org.json.JSONArray;

// Java
import java.util.TreeSet;

// Log4J
import org.apache.log4j.Logger;

/** <code>Nodes</code> is {@link TreeSet} representation of
  * <a href="http://visjs.org">vis.js</a> <em>Node</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Nodes extends TreeSet<Node> {
  
  /** TBD */
  public JSONArray toJSONArray() {
    JSONArray ja = new JSONArray();
    for (Node node : this) {
      ja.put(node);
      }
    return ja;
    }
      
  /** Logging . */
  private static Logger log = Logger.getLogger(Nodes.class);
   
  }
