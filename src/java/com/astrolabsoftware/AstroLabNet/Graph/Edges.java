package com.astrolabsoftware.AstroLabNet.Graph;

// org.json
import org.json.JSONArray;

// Java
import java.util.TreeSet;

// Log4J
import org.apache.log4j.Logger;

/** <code>Edges</code> is {@link TreeSet} representation of
  * <a href="http://visjs.org">vis.js</a> <em>Edge</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Edges extends TreeSet<Edge> {
  
  /** TBD */
  public Edges() {
    super();
    }
  
  /** TBD */
  public JSONArray toJSONArray() {
    JSONArray ja = new JSONArray();
    for (Edge edge : this) {
      ja.put(edge);
      }
    return ja;
    }
        
  /** Logging . */
  private static Logger log = Logger.getLogger(Edges.class);
   
  }
