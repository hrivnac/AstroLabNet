package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.ScrollPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

// org.json
import org.json.JSONObject;

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
    super(name, browser, Images.SESSION);
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
    MenuItem execute = new MenuItem("Prepare for Execution",  Images.icon(Images.SESSION));
    String tit = toString();
    execute.setOnAction(new SessionEventHandler(this));
    menuItems.add(execute);
    return menuItems;
    }
    
  /** Set reference to result.
    * To be filled once available.
    * @param resultRef The result {@link TextFlow} shown in the {@link Session} tab.*/
  public void setResultRef(TextFlow resultRef) {
    _resultRef = resultRef;
    }
    
  /** Set result into result reference on the {@link Session} tab.
    * @param result The result {@link Text}. */
  public void setResult(Text result) {
    if (_resultRef == null) {
      addTab();
      }
    else {
      _resultRef.getChildren().add(result);
      }
    }
    
  /** Add {@link Tab} of this Session. */
  public void addTab() {
    // Desc
    Label desc = new Label("Command in " + _language + ":");
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
    Session session = this;
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        String result = _server.livy().sendCommand(_id, cmd.getText());
        int id = new JSONObject(result).getInt("id");;
        browser().addTask(_server.urlLivy() + "/" + _id + "/" + id, session, id);
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
    
  @Override
  public String toString() {
    return name() + " : " + _id + " in " + _language;
    }
    
  private int _id;
  
  private Language _language;
  
  private Server _server;
  
  private TextFlow _resultRef;
  
  private ProgressBar _progress;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Session.class);

  }
