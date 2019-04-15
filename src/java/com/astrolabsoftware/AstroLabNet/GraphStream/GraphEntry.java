package com.astrolabsoftware.AstroLabNet.GraphStream;

// GraphStream
import org.graphstream.graph.Graph;

// Java
import java.util.Map;
import java.util.HashMap;

// Log4J
import org.apache.log4j.Logger;

/** The <em>GraphEntry</em> represents one <em>HBase</em> entry.
  * To be used in <em>GraphStream</em> {@link Graph}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class GraphEntry {
  
  /** Create.
    * @param id The entry id. */
  public GraphEntry(String id) {
    _id = id;
    }
  
  /** Add one piece of content.
    * @param name  The item name.
    * @param value The item value. */
  public void addContent(String name,
                         String value) {
    _content.put(name, value);
    }
    
  /** Give full content.
    * @return The full content. */
  public String content() {
    String content = "id: " + _id + "\n";
    for (Map.Entry<String, String> e : _content.entrySet()) {
      content += e.getKey() + ": " + e.getValue() + "\n";
      }
    return content;
    }
    
  /** Give entry id.
    * @return The entry id. */
  public String id() {
    return _id;
    }
    
  private String _id;
  
  private Map<String, String> _content   = new HashMap<>();
  
  /** Logging . */
  private static Logger log = Logger.getLogger(GraphEntry.class);
 
  }
