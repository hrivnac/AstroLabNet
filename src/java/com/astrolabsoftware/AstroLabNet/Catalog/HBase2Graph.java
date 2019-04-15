package com.astrolabsoftware.AstroLabNet.Catalog;

import com.astrolabsoftware.AstroLabNet.Utils.Coding;

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

/** <code>HBase2Graph</code> interprets <em>HBase Catalog</em> data
  * as a <em> GraphStream</em> graph.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class HBase2Graph {
    
  /** Convert <em>Catalog</em> {@link JSONObject} into <em>GraphStream</em> {@link Graph}. */
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
    CatalogEntry entry;
    Node node;
    List<CatalogEntry> entries = new ArrayList<>();
    //for (int i = 0; i < rows.length(); i++) {
    for (int i = 0; i < 10; i++) {
      entry = new CatalogEntry(Coding.decode(rows.getJSONObject(i).getString("key")));
      cells = rows.getJSONObject(i).getJSONArray("Cell");
      for (int j = 0; j < cells.length(); j++) {
        column = Coding.decode(cells.getJSONObject(j).getString("column"));
        value  = Coding.decode(cells.getJSONObject(j).getString("$"));
        if (column.startsWith("r:")) {
          _relations.put(column.substring(2), entry.id() + ":" + value);
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
        graph.addEdge(e.getValue().split(":")[0], e.getValue().split(":")[1], e.getKey(), true);
        }
      catch (ElementNotFoundException ex) {
        log.error("Cannot create Edge TBD");
        }
      }
    }
  
  private Map<String, String> _relations = new HashMap<>();
  
  /** Logging . */
  private static Logger log = Logger.getLogger(HBase2Graph.class);

  }
