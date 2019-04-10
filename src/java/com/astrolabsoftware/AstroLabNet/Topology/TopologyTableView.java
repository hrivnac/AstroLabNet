package com.astrolabsoftware.AstroLabNet.Journal;

import com.astrolabsoftware.AstroLabNet.Utils.Coding;

// JavaFX
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

// org.json
import org.json.JSONObject;
import org.json.JSONArray;

// Java

// Log4J
import org.apache.log4j.Logger;

/** The <em>TopologyTableView</em> is a {@link TableView} of {@link TopologyEntry}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
// TBD: make this general, for any type
// TBD: replace case switcxh with ENTRY_NAMES
public class TopologyTableView extends TableView {

  /** Create and initialise structure. */
  public TopologyTableView() {
    super();
    TableColumn<String, TopologyEntry> column;
    for (String entryName : ENTRY_NAMES) {
      column = new TableColumn<>(entryName);
      column.setCellValueFactory(new PropertyValueFactory<>(entryName));
      getColumns().add(column);
      }
    }
    
  /** Fill in {@link JSONObject}.
    * @param json The {@link JSONObject} to fill in. */
  public void addJSONEntry(JSONObject json) {
    if (json == null || json.equals("")) {
      return;
      }
    JSONArray rows = json.getJSONArray("Row");
    JSONArray cells;
    String key;
    String column;
    String value;
    TopologyEntry entry = null;
    for (int i = 0; i < rows.length(); i++) {
      entry = new TopologyEntry();
      entry.setKey(Coding.decode(rows.getJSONObject(i).getString("key")));
      cells = rows.getJSONObject(i).getJSONArray("Cell");
      for (int j = 0; j < cells.length(); j++) {
        column = Coding.decode(cells.getJSONObject(j).getString("column"));
        value  = Coding.decode(cells.getJSONObject(j).getString("$"));
        switch (column) { // TBD: befire/after should be r:
          case "i:name":
            entry.setName(value);
            break;
          case "i:location":
            entry.setLocation(value);
            break;
          case "d:spark":
            entry.setSpark(value);
            break;
          case "d:livy":
            entry.setLivy(value);
            break;
          case "d:hbase":
            entry.setHbase(value);
            break;
          case "c:comment":
            entry.setComment(value);
            break;
          default:
            log.error("Cannot show " + column + " = " + value); 
          }
        }
      if (entry != null) {
        getItems().add(entry);
        }
      }
    }
    
    
  private static String[] ENTRY_NAMES = new String[]{"key", "name", "location", "spark", "livy", "hbase", "comment"};
  
  /** Logging . */
  private static Logger log = Logger.getLogger(TopologyTableView.class);
 
  }
