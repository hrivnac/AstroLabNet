package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

// org.json
import org.json.JSONObject;
import org.json.JSONArray;

// Java
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// Log4J
import org.apache.log4j.Logger;

/** <code>Task</code> represents a task running or finished on <em>Spark</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Task extends Element {  
    
  /** Create new Task.
    * Check the progress.
    * @param name    The Task name.
    * @param session The hosting {@link Session}.
    * @param id      The statement id. */
  public Task(String     name,
              Session    session,
              int        id) {
    super(name);
    _session = session;
    _id      = id;
    }
  
  /** Give hosting {@link Session}.
    * @return The hosting {@link Session}. */
  public Session session() {
    return _session;
    }
    
  /** Give the hosting {@link Session} id.
    * @return The hosting {@link Session} id. */
  public int id() {
    return _id;
    }
     
  @Override
  public String toString() {
    return name();
    }
   
  private Session _session;  
  
  private int _id;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(Task.class);

  }
