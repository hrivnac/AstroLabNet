package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Livyser.LivyRI;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.scene.control.TreeItem;

// Log4J
import org.apache.log4j.Logger;

/** <code>Server</code> represents <em>Livy</em> server.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Server extends Element {
  
  public Server(String        name,
                String        urlLivy,
                String        urlSpark) {
    super(name);
    _urlLivy    = urlLivy;
    _urlSpark   = urlSpark;
    _livy       = new LivyRI(urlLivy);
    _item       = new TreeItem<Element>(this, Images.icon(Images.LIVY));
    }
        
  public String urlLivy() {
    return _urlLivy;
    }
        
  public String urlSpark() {
    return _urlSpark;
    }  
    
  public void use() {
    _livy.initSession();
    updateSessions();
    }

  public void updateSessions() {
    item().getChildren().clear();
    for (int id : _livy.getSessions()) {
      item().getChildren().add(new TreeItem<Element>(new Session("Session", id)));
      }
    }
    
  public TreeItem<Element> item() {
    return _item;
    }
    
  public String toString() {
    return name() + " (Livy = " + _urlLivy + ", Spark = " + _urlSpark + ")";
    }
    
  private String _urlLivy;
  
  private String _urlSpark;
  
  private TreeItem<Element> _item;
  
  private LivyRI _livy;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Server.class);

  }
