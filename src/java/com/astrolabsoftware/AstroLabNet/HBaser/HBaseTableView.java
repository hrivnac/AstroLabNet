package com.astrolabsoftware.AstroLabNet.HBaser;

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

/** The <em>HBaseTableView</em> is a {@link TableView} for <em>HBase</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class HBaseTableView<T extends TableEntry> extends TableView {

  /** Create and initialise structure. */
  public HBaseTableView() {
    super();
    }
    
  /** Set names of columns.
    * @param entryNames The names of columns. */
  public void setEntryNames(String[] entryNames) {
    _entryNames = entryNames;
    TableColumn<String, TableEntry> column;
    for (String entryName : _entryNames) {
      column = new TableColumn<>(entryName);
      column.setCellValueFactory(new PropertyValueFactory<>(entryName));
      getColumns().add(column);
      }
    }
    
  /** Fill in {@link JSONObject}.
    * @param json The {@link JSONObject} to fill in. 
    * @param class The actual {@link Class} of the {@link TableView}. */
  public void addJSONEntry(JSONObject json,
                           Class<T>   clazz) {
    if (json == null || json.equals("")) {
      log.error("Empty json");
      return;
      }
    if (_entryNames == null) {
      log.error("Entry names not set");
      return;
      }
    JSONArray rows = json.getJSONArray("Row");
    JSONArray cells;
    String key;
    String column;
    String value;
    T entry = null;
    for (int i = 0; i < rows.length(); i++) {
      try {
        entry = clazz.newInstance();
        }
      catch (InstantiationException | IllegalAccessException e) {
        log.error("Cannot instatiate TableEntry", e);
        return;
        }
      entry.set("x:key", Coding.decode(rows.getJSONObject(i).getString("key")));
      cells = rows.getJSONObject(i).getJSONArray("Cell");
      for (int j = 0; j < cells.length(); j++) {
        column = Coding.decode(cells.getJSONObject(j).getString("column"));
        value  = Coding.decode(cells.getJSONObject(j).getString("$"));
        if (column.equals("b")) {
          entry.set(column, "*binary*"); // TBD: process and handle
          }
        else {
          entry.set(column, value); // TBD: hadle r:
          }
        }
      if (entry != null) {
        getItems().add(entry);
        }
      }
    }
    
  private String[] _entryNames = null;
      
  /** Logging . */
  private static Logger log = Logger.getLogger(HBaseTableView.class);
 
  }
