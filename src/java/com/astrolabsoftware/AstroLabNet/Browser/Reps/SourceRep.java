package com.astrolabsoftware.AstroLabNet.Browser.Reps;

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

/** <code>SourceRep</code> is {@link BrowserWindow} representation of {@link Source}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class SourceRep extends ElementRep {
  
  /** Create new SourceRep as a <em>Singleton</em>.
    * @param source The original {@link Source}.
    * @param browser The {@link BrowserWindow}. */
  public static SourceRep create(Source        source,
                                 BrowserWindow browser) {
    SourceRep sourceRep = _sourceReps.get(source.toString());
    if (sourceRep == null) {
      sourceRep = new SourceRep(source, browser);
      log.info("Adding Source " + sourceRep);
      _sourceReps.put(source.toString(), sourceRep);
      }
    return sourceRep;
    }
  
  private static Map<String, SourceRep> _sourceReps = new HashMap<>();  
  
  /** Create new SourceRep.
    * @param source  The represented {@link Source}.
    * @param browser The {@link BrowserWindow}. */
  public SourceRep(Source        source,
                   BrowserWindow browser) {
    super(source, browser, Images.SOURCE);
    }
    
  /** Give the keeping {@link ServerRep}.
    * @return The keeping {@link ServerRep}. */
  public ServerRep serverRep() {
    return ServerRep.create(server(), browser());
    }
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem execute = new MenuItem("Prepare for Search",  Images.icon(Images.SESSION));
    execute.setOnAction(new SourceEventHandler(this));
    menuItems.add(execute);
    return menuItems;
    }
    
  /** Set reference to result.
    * To be filled once available.
    * @param resultRef The result {@link TextFlow} shown in the {@link SourceRep} tab.*/
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
    
  /** Add {@link Tab} of this SourceRep. */
  public void addTab() {
    // Desc
    Label desc = new Label("Search");
    // Cmd
    TextArea cmd = new TextArea();
    cmd.setPrefHeight(2000);
    // Button
    Button button = new Button("Execute");
    // ButtonBox = Progress + Button
    HBox buttonBox = new HBox(10);
    buttonBox.setSpacing(5);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().addAll(button);
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
    SourceRep sourceRep = this;
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        String result = serverRep().hbase().scan("astrolabnet.catalog.1"); // TBD: via params
        browser().command().addSearch(serverRep().name(), sourceRep.source());
        resultText.getChildren().add(new Text("Query send to Source\n\n"));
        JSONObject resultObject = new JSONObject(result);
        resultText.getChildren().add(new Text(resultObject.toString(2).replaceAll("\\\\n", "") + "\n\n"));
        }
      });
    setResultRef(resultText);
    Tab tab = browser().addTab(pane, toString(), Images.SOURCE);
    browser().registerSourceTab(this, tab);
    }

  /** Give the SessionRep keeping {@link Server}.
    * @return The SessionRep keeping {@link Server}. */
  public Server server() {
    return source().server();
    }
    
  /** Give the referenced {@link Source}.
    * @return The referenced {@link Source}. */
  public Source source() {
    return (Source)element();
    }
    
  @Override
  public String toString() {
    return source().toString();
    }
  
  private TextFlow _resultRef;
   
  /** Logging . */
  private static Logger log = Logger.getLogger(SourceRep.class);

  }
