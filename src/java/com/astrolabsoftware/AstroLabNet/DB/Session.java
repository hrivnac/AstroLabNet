package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

// Java
import java.util.List;

// Log4J
import org.apache.log4j.Logger;

/** <code>Session</code> represents <em>Livy</em> session.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Session extends Element {
  
  /** Create new Session.
    * @param name    The Session name.
    * @param browser The {@link BrowserWindow}.
    * @param id      The Session id.
    * @param sefrver The {@link Server} keeping this Session. */
  public Session(String        name,
                 BrowserWindow browser,
                 int           id,
                 Language      language,
                 Server        server) {
    super(name, browser);
    _id       = id;
    _language = language;
    _server   = server;
    }
    
  /** Give the Session id.
    * @return The Session id. */
  public int id() {
    return _id;
    }
    
  /** Give the Session {@link Language}.
    * @return The Session {@link Language}. */
  public Language language() {
    return _language;
    }
    
  /** Give the keeping {@link Server}.
    * @return The keeping {@link Server}. */
  public Server server() {
    return _server;
    }
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem execute = new MenuItem("Prepare for Execution",  Images.icon(Images.USE));
    String tit = toString();
    execute.setOnAction(new SessionEventHandler(this));
    menuItems.add(execute);
    return menuItems;
    }
    
  /** Set reference to result.
    * To be filled once available.
    * @param resultRef The result {@link Text} shown in the {@link Session} tab.*/
  public void setResultRef(Text resultRef) {
    _resultRef = resultRef;
    }
    
  /** Set result into result reference on the {@link Session} tab.
    * @param result The result text. */
  public void setResult(String result) {
    _resultRef.setText(result);
    }
    
  @Override
  public String toString() {
    return name() + " : " + _id + " in " + _language;
    }
    
  private int _id;
  
  private Language _language;
  
  private Server _server;
  
  private Text _resultRef;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Session.class);

  }
