package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Livyser.LivyRI;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// JavaFX
import javafx.scene.control.TreeItem;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.util.Pair;

// Java
import java.util.List;

// Log4J
import org.apache.log4j.Logger;

/** <code>Server</code> represents <em>Livy</em> server.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Server extends Element {
  
  /** Create new Spark and Livy Server.
    * @param name The Server name.
    * @param urlLivy  The url of the Spark Server Livy interface.
    * @param urlSpark The url of the Spark Server. */
  public Server(String        name,
                String        urlLivy,
                String        urlSpark) {
    super(name, Images.LIVY);
    _urlLivy    = urlLivy;
    _urlSpark   = urlSpark;
    _livy       = new LivyRI(urlLivy);
    }
        
  /** Give Spark Server Livy interface url.
    * @return The Spark Server Livy interface url. */
  public String urlLivy() {
    return _urlLivy;
    }
        
  /** Give Spark Server url.
    * @return The Spark Server url. */
  public String urlSpark() {
    return _urlSpark;
    }  
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem pythonSession = new MenuItem("Python Session",  Images.icon(Images.USE));
    MenuItem scalaSession  = new MenuItem("Scala Session",  Images.icon(Images.USE));
    MenuItem rSession      = new MenuItem("R Session",  Images.icon(Images.USE));
    MenuItem sqlSession    = new MenuItem("SQL Session",  Images.icon(Images.USE));
    pythonSession.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                  _livy.initSession(Language.PYTHON);
                                  updateSessions();
                                  }
                                });
    scalaSession.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                  _livy.initSession(Language.SCALA);
                                  updateSessions();
                                  }
                                });
    rSession.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                  _livy.initSession(Language.R);
                                  updateSessions();
                                  }
                                });
    sqlSession.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                  _livy.initSession(Language.SQL);
                                  updateSessions();
                                  }
                                });
    menuItems.add(pythonSession);
    menuItems.add(scalaSession);
    menuItems.add(rSession);
    menuItems.add(sqlSession);
    return menuItems;
    }

  /** Update list of dependent {@link Session}s. */
  public void updateSessions() {
    item().getChildren().clear();
    for (Pair<Integer, Language> p : _livy.getSessions()) {
      item().getChildren().add(new TreeItem<Element>(new Session("Session", p.getKey(), p.getValue())));
      }
    }
    
  @Override
  public String toString() {
    return name() + " (Livy = " + _urlLivy + ", Spark = " + _urlSpark + ")";
    }
    
  private String _urlLivy;
  
  private String _urlSpark;
  
  private LivyRI _livy;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Server.class);

  }
