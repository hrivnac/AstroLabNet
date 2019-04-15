package com.astrolabsoftware.AstroLabNet.Catalog;

// GraphStream
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.AbstractNode;
import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.ElementNotFoundException;

// Java
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

// Log4J
import org.apache.log4j.Logger;

/** The <em>CatalogEntry</em> represents one <em>Catalog</em> entry.
  * To be used in <em>GraphStream</em> {@link Graph}.
  * (It is a <em>JavaBean</em>.) 
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class CatalogEntry {
  
  /** TBD */
  public CatalogEntry(String id) {
    _id = id;
    }
  
  /** TBD */
  public void addContent(String name,
                         String address) {
    _content.put(name, address);
    }
    
  /** TBD */
  public String content() {
    return _content.toString();
    }
    
  /** TBD */
  public String id() {
    return _id;
    }
    
  private String _id;
  
  private Map<String, String> _content   = new HashMap<>();
  
  /** Logging . */
  private static Logger log = Logger.getLogger(CatalogEntry.class);
 
  }
