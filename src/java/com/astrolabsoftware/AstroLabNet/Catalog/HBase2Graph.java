package com.astrolabsoftware.AstroLabNet.Catalog;

// GraphStream
import org.graphstream.graph.Graph;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

// org.json
import org.json.JSONObject;
import org.json.JSONArray;

// Log4J
import org.apache.log4j.Logger;

/** <code>HBase2Graph</code> interprets <em>HBase Catalog</em> data
  * as a <em> GraphStream</em> graph.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class HBase2Graph {
    
  /** TBD */
  public void updateGraph(JSONObject json,
                          Graph graph) {
    graph.addNode("A" );
    graph.addNode("B" );
    graph.addNode("C" );
    graph.addEdge("AB", "A", "B");
    graph.addEdge("BC", "B", "C");
    graph.addEdge("CA", "C", "A");
    for (Node node : graph) {
      node.setAttribute("ui.label", node.getId());
      }
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(HBase2Graph.class);

  }
