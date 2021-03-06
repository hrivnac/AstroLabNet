package com.astrolabsoftware.AstroLabNet.GraphStream;

// JHTools
import com.JHTools.Utils.Coding;

// GraphStream
import org.graphstream.graph.Graph;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.ElementNotFoundException;

// org.json
import org.json.JSONObject;
import org.json.JSONArray;

// Java
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

// Log4J
import org.apache.log4j.Logger;

/** <code>HBase2Graph</code> interprets <em>HBase</em> data
  * as a <em> GraphStream</em> graph.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class HBase2Graph {
    
  /** Convert <em>HBase</em> {@link JSONObject} into <em>GraphStream</em> {@link Graph}. */
  public void updateGraph(JSONObject json,
                          Graph      graph) {
    if (json == null || json.equals("")) {
      return;
      }
    JSONArray rows = json.getJSONArray("Row");
    JSONArray cells;
    String key;
    String column;
    String value;
    GraphEntry entry;
    Node node;
    List<GraphEntry> entries = new ArrayList<>();
    for (int i = 0; i < rows.length(); i++) {
      entry = new GraphEntry(Coding.decode(rows.getJSONObject(i).getString("key")));
      cells = rows.getJSONObject(i).getJSONArray("Cell");
      for (int j = 0; j < cells.length(); j++) {
        column = Coding.decode(cells.getJSONObject(j).getString("column"));
        value  = Coding.decode(cells.getJSONObject(j).getString("$"));
        if (column.startsWith("r:")) {
          _relations.put(column.substring(2), entry.id() + ":" + value);
          }  
        else if (column.startsWith("b")) {
          entry.addContent(column.substring(2), "*binary*"); // TBD: process
          }
        else {
          entry.addContent(column.substring(2), value);
          }
        }
      node = graph.addNode(entry.id());
      node.setAttribute("ui.label", entry.id());
      node.setAttribute("content", entry.content());
      entries.add(entry);
      }
    for (Map.Entry<String, String> e : _relations.entrySet()) {
      try {
        graph.addEdge(e.getKey(), e.getValue().split(":")[0], e.getValue().split(":")[1], true);
        }
      catch (ElementNotFoundException ex) {
        log.error("Cannot create Edge " + e.getKey() + ": " + e.getValue().split(":")[0] + " -> " + e.getValue().split(":")[1], ex);
        }
      }
    }
  
  private Map<String, String> _relations = new HashMap<>();
  
  /** Logging . */
  private static Logger log = Logger.getLogger(HBase2Graph.class);

  }
