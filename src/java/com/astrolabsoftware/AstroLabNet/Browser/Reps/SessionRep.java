package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.ScrollPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;

// org.json
import org.json.JSONObject;

// Java
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// Log4J
import org.apache.log4j.Logger;

/** <code>SessionRep</code> is {@link BrowserWindow} representation of {@link Session}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class SessionRep extends ElementRep {
  
  /** Create new SessionRep as a <em>Singleton</em>.
    * @param session The original {@link Session}.
    * @param browser The {@link BrowserWindow}. */
  public static SessionRep create(Session        session,
                                  BrowserWindow  browser) {
    SessionRep sessionRep = _sessionReps.get(session.toString());
    if (sessionRep == null) {
      sessionRep = new SessionRep(session, browser);
      log.info("Adding Session " + sessionRep);
      _sessionReps.put(session.toString(), sessionRep);
      }
    return sessionRep;
    }
  
  private static Map<String, SessionRep> _sessionReps = new HashMap<>();  
  
  /** Create new SessionRep.
    * @param session   The represented {@link Session}.
    * @param browser   The {@link BrowserWindow}. */
  public SessionRep(Session session,
                    BrowserWindow browser) {
    super(session, browser, Images.SESSION);
    }
    
  /** Give the keeping {@link ServerRep}.
    * @return The keeping {@link ServerRep}. */
  public ServerRep serverRep() {
    return ServerRep.create(server(), browser());
    }
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem execute = new MenuItem("Prepare for Execution",  Images.icon(Images.SESSION));
    String tit = toString();
    execute.setOnAction(new SessionEventHandler(this));
    menuItems.add(execute);
    return menuItems;
    }
    
  /** Set reference to result.
    * To be filled once available.
    * @param resultRef The result {@link TextFlow} shown in the {@link SessionRep} tab.*/
  public void setResultRef(TextFlow resultRef) {
    _resultRef = resultRef;
    }
    
  /** Set result into result reference on the {@link SessionRep} tab.
    * @param result The result {@link Text}. */
  public void setResult(Text result) {
    if (_resultRef == null) {
      addTab();
      }
    else {
      _resultRef.getChildren().add(result);
      }
    }
    
  /** Add {@link Tab} of this SessionRep. */
  public void addTab() {
    // Desc
    Label desc = new Label("Command in " + language() + ":");
    // Cmd
    TextArea cmd = new TextArea();
    cmd.setPrefHeight(2000);
    // Progress
    _progress = new ProgressBar(0);
    // Button
    Button button = new Button("Execute");
    // ButtonBox = Progress + Button
    HBox buttonBox = new HBox(10);
    buttonBox.setSpacing(5);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().addAll(_progress, button);
    // CmdBox = Desc + Cmd + ButtonBox 
    VBox cmdBox = new VBox();
    cmdBox.setSpacing(5);
    cmdBox.setAlignment(Pos.CENTER);
    cmdBox.getChildren().addAll(desc, cmd, buttonBox);
    // ResultText
    TextFlow resultText = new TextFlow(); 
    Text result0 = new Text("Fill in or select Action\n\n");
    result0.setFill(Color.DARKGREEN);
    resultText.getChildren().add(result0);
    // ScrollPane = ResultText
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setFitToWidth(true);
    scrollPane.setContent(resultText);
    // Pane = Desc + Cmd + ButtonBox + ScrollPane
    SplitPane pane = new SplitPane();
    pane.setDividerPositions(0.5);
    pane.setOrientation(Orientation.VERTICAL);
    pane.getItems().addAll(cmdBox, scrollPane);
    SessionRep sessionRep = this;
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        String result = serverRep().livy().sendCommand(id(), cmd.getText());
        int id = new JSONObject(result).getInt("id");
        browser().command().addTask(serverRep().urlLivy() + "/" + id() + "/" + id, sessionRep.session(), id);
        resultText.getChildren().add(new Text("Command send to Session\n\n"));
        }
      });
    setResultRef(resultText);
    Tab tab = browser().addTab(pane, toString(), Images.SESSION);
    browser().registerSessionTab(this, tab);
    }
    
  /** Set {@link ProgressBar} value.
    * @param p The {@link ProgressBar} value = &lt;0,1&gt;. */
  public void setProgress(double p) {
    if (_progress == null) {
      return;
      }
    _progress.setProgress(p);
    }   
    
  /** Give the SessionRep id.
    * @return The SessionRep id. */
  public int id() {
    return session().id();
    }
    
  /** Give the SessionRep {@link Language}.
    * @return The SessionRep {@link Language}. */
  public Language language() {
    return session().language();
    }
    
  /** Give the SessionRep keeping {@link Server}.
    * @return The SessionRep keeping {@link Server}. */
  public Server server() {
    return session().server();
    }
 
  /** Give the referenced {@link Session}.
    * @return The referenced {@link Session}. */
  public Session session() {
    return (Session)element();
    }
   
  @Override
  public String toString() {
    return session().toString();
    }
  
  private TextFlow _resultRef;
  
  private ProgressBar _progress;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(SessionRep.class);

  }
