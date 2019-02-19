package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Livyser.LivyRESTClient;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;
import com.astrolabsoftware.AstroLabNet.DB.*;

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

/** <code>ServerRep</code> is {@link BrowserWindow} representation of {@link Server}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ServerRep extends ElementRep {
  
  /** Create new Spark and Livy Server.
    * @param server   The represented {@link Server}.
    * @param browser  The {@link BrowserWindow}. */
  public ServerRep(Server        server,
                   BrowserWindow browser) {
    super(server, browser, Images.LIVY);
    }
        
  /** Give Spark Server Livy interface url.
    * @return The Spark Server Livy interface url. */
  public String urlLivy() {
    return server().urlLivy();
    }
        
  /** Give Spark Server url.
    * @return The Spark Server url. */
  public String urlSpark() {
    return server().urlSpark();
    }  
    
  /** Give Livy Server url.
    * @return The Livy Server url. */
  public LivyRESTClient livy() {
    return server().livy();
    }
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem pythonSession = new MenuItem("Python Session", Images.icon(Images.SESSION));
    MenuItem scalaSession  = new MenuItem("Scala Session",  Images.icon(Images.SESSION));
    MenuItem rSession      = new MenuItem("R Session",      Images.icon(Images.SESSION));
    MenuItem sqlSession    = new MenuItem("SQL Session",    Images.icon(Images.SESSION));
    pythonSession.setOnAction(new ServerEventHandler(this, Language.PYTHON));
    scalaSession.setOnAction( new ServerEventHandler(this, Language.SCALA ));
    rSession.setOnAction(     new ServerEventHandler(this, Language.R     ));
    sqlSession.setOnAction(   new ServerEventHandler(this, Language.SQL   ));
    menuItems.add(pythonSession);
    menuItems.add(scalaSession);
    menuItems.add(rSession);
    menuItems.add(sqlSession);
    updateSessions();
    return menuItems;
    }

  /** Update list of dependent {@link Session}s. */
  public void updateSessions() {
    item().getChildren().clear();
    int idSession;
    SessionRep sessionRep;
    for (Pair<Integer, Language> p : server().livy().getSessions()) {
      idSession = p.getKey();
      sessionRep = SessionRep.create(new Session("Session",  idSession, p.getValue(), server()), browser());
      item().getChildren().add(new TreeItem<ElementRep>(sessionRep));
      for (int idStatement : server().livy().getStatements(idSession)) {
        browser().addTask(server().urlLivy() + "/" + idSession + "/" + idStatement, sessionRep.session(), idStatement);
        }
      }
    }
    
  /** TBD */
  public Server server() {
    return (Server)element();
    }
    
  @Override
  public String toString() {
    return server().toString();
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(ServerRep.class);

  }
