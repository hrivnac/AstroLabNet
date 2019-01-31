package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Livyser.LivyRI;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// JavaFX
import javafx.scene.control.TreeItem;

// Log4J
import org.apache.log4j.Logger;

// JavaFX
import javafx.scene.image.Image;

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
    
  /** Create new Spark {@link Session}. */
  @Override
  public void use() {
    _livy.initSession();
    updateSessions();
    }

  /** Update list of dependent {@link Session}s. */
  public void updateSessions() {
    item().getChildren().clear();
    for (int id : _livy.getSessions()) {
      item().getChildren().add(new TreeItem<Element>(new Session("Session", id)));
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
