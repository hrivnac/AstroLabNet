package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.ReadJARHandler;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import org.json.JSONArray;

// Java
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes;
import java.util.Iterator;

// Log4J
import org.apache.log4j.Logger;

/** <code>BatchRep</code> is {@link BrowserWindow} representation of {@link Batch}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class BatchRep extends ElementRep {
  
  /** Create new BatchRep as a <em>Singleton</em>,
    * if it has id > 0.
    * @param batch   The original {@link Batch}.
    * @param browser The {@link BrowserWindow}. */
  public static BatchRep create(Batch          batch,
                                BrowserWindow  browser) {
    BatchRep batchRep = null;
    if (batch.id() > 0) {
      batchRep = _batchReps.get(batch.toString());
      }
    if (batchRep == null) {
      batchRep = new BatchRep(batch, browser);
      if (batch.id() > 0) {
        log.info("Adding Batch " + batchRep);
        _batchReps.put(batch.toString(), batchRep);
        }
      }
    return batchRep;
    }
  
  /** TBD */
  private void put(String   batchName) {
    _batchReps.put(batchName, this);
    }
    
  private static Map<String, BatchRep> _batchReps = new HashMap<>();  
  
  /** Create new BatchRep.
    * Check the progress.
    * @param name       The represented {@link Batch}.
    * @param browser    The {@link BrowserWindow}. */
  public BatchRep(Batch        batch,
                  BrowserWindow browser) {
    super(batch, browser, Images.BATCH);
    Thread thread = new Thread() {
      // check periodically status, untill progress = 1.0
      // then leave the thread and report results
      @Override
      public void run() {
        String resultString;
        JSONObject result;
        String statex;
        while (true) {
          try {
            if (batch.id() > 0) {
              resultString = serverRep().livy().checkBatchProgress(batch.id(), 10, 1);
              if (resultString != null) {
                result = new JSONObject(resultString);
                statex = result.getString("state");
                setState(statex);
                log.debug("State = " + statex);
                if (statex.equals("success") || statex.equals("dead")) { // TBD: handle failure
                  break;
                  }
                }
              }
            Thread.sleep(1000); // 1s
            }
          catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            }          
          }
        JSONArray logArray = result.getJSONArray("log");
        String fullLog = "";
        for (Object logEntry : logArray) {
          fullLog += logEntry.toString() + "\n";
          }
        String state = result.getString("state");
        Text text = new Text();
        if (state.equals("success")) {
          text.setFill(Color.DARKBLUE);
          }
        else if (state.equals("dead")) {
          text.setFill(Color.DARKRED);
          }
        else {
          log.error("Unknown state " + state);
          }
        log.info(state + " : " + fullLog);
        final String fullLog1 = fullLog; // so it can go to inner fcion
        // to synchronise threads
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            text.setText("state = " + state + "\n\nlog = " + fullLog1.replaceAll("\\\\n", "") + "\n\n");
            setResult(text); // check, if the selected tab is correct
            }
          });
        }
      };
    thread.start();
    }
    
  /** Add {@link Tab} for new {@link Job} */
  public void addTab() {
    // Desc
    Label desc = new Label("Job:");
    // JAR
    Button jar = new SimpleButton("JAR file", Images.JAR, "Load JAR file", new ReadJARHandler(this));
    // File
    _file = new TextField();
    _file.setPrefColumnCount(50);
    // ClassName
    _className = new TextField();
    _className.setPrefColumnCount(50);
    // JobBox = JAR + File + ClassName
    HBox jobBox = new HBox(10);
    jobBox.setSpacing(5);
    jobBox.setAlignment(Pos.CENTER);
    jobBox.getChildren().addAll(jar, _file, _className);
    // Progress
    _progress = new ProgressBar(0);
    // Button
    Button button = new Button("Send");
    // ButtonBox = Progress + Button
    HBox buttonBox = new HBox(10);
    buttonBox.setSpacing(5);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().addAll(_progress, button);
    // CmdBox = Desc + JobBox + ButtonBox 
    VBox cmdBox = new VBox();
    cmdBox.setSpacing(5);
    cmdBox.setAlignment(Pos.CENTER);
    cmdBox.getChildren().addAll(desc, jobBox, buttonBox);
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
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        int id = serverRep().livy().sendJob("local:" + _file.getText(), _className.getText(), Integer.MAX_VALUE, 1);
        batch().setId(id);
        put(batch().toString());
        browser().command().addBatch(batch().name(), batch().server(), batch().id());
        resultText.getChildren().add(new Text("Job send\n\n"));
        }
      });
    setResultRef(resultText);
    Tab tab = browser().addTab(pane, toString(), Images.SESSION);
    browser().registerBatchTab(this, tab);
    }
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem show = new MenuItem("Show Job",  Images.icon(Images.JOB));
    show.setOnAction(new BatchEventHandler(this));
    menuItems.add(show);
    return menuItems;
    }  
  /** Set reference to result.
    * To be filled once available.
    * @param resultRef The result {@link TextFlow} shown in the {@link SessionRep} tab.*/
  public void setResultRef(TextFlow resultRef) {
    _resultRef = resultRef;
    }
    
  /** Set result into result reference tab.
    * @param result The result {@link Text}. */
  public void setResult(Text result) {
    if (_resultRef == null) {
      addTab();
      }
    else {
      _resultRef.getChildren().add(result);
      }
    }
    
  /** Set {@link ProgressBar} value.
    * @param p The {@link ProgressBar} value = 0,1. */ // TBD: make just 0-1 light
  public void setState(String s) {
    if (_progress == null) {
      return;
      }
    if (s.equals("starting")) {
      _progress.setProgress(0.0);
      }
    else if (s.equals("running")) {
      _progress.setProgress(0.5);
      }
   else if (s.equals("success")) {
      _progress.setProgress(1.0);
      }
   else if (s.equals("dead")) {
      _progress.setProgress(1.0);
      }
    else {
      log.error("Cannot handle state " + s);
      }
    }   
  
  /** Set chosen jar files and its <em>Main-Class</em>, if possible.
    * @param file The chosen jar file. */
  public void setFile(File file) {
    try {
      String fn = file.getCanonicalPath();
      log.info("Opening " + fn + ":");
      _file.setText(fn);
      _className.setText("not specified in JAR");
      JarFile jarfile = new JarFile(file);
      Manifest manifest = jarfile.getManifest();
      Attributes attributes = (Attributes)manifest.getMainAttributes();
      Attributes.Name attrName;
      String attrValue;
      for (Iterator it = attributes.keySet().iterator(); it.hasNext();) {
        attrName = (Attributes.Name)it.next();
        attrValue = attributes.getValue(attrName);
        log.info("  " + attrName + " : " + attrValue);
        //if (attrName == Attributes.Name.MAIN_CLASS) {
        if (attrName.toString().trim().equals("Main-Class")) {
          _className.setText(attrValue);
          }
        }
      }
    catch (IOException e) {
      log.error("Cannot get " + file, e);
      }
    }
    
  /** Give the BatchRep id.
    * @return The BatchRep id. */
  public int id() {
    return batch().id();
    }
    
  /** Give the keeping {@link ServerRep}.
    * @return The keeping {@link ServerRep}. */
  public ServerRep serverRep() {
    return ServerRep.create(server(), browser());
    }
        
  /** Give the BatchRep keeping {@link Server}.
    * @return The BatchRep keeping {@link Server}. */
  public Server server() {
    return batch().server();
    }
 
  /** Give the referenced {@link Batch}.
    * @return The referenced {@link Batch}. */
  public Batch batch() {
    return (Batch)element();
    }
   
  @Override
  public String toString() {
    return batch().toString();
    }
    
  private TextField _file;  
    
  private TextField _className;  
    
  private TextFlow _resultRef;
  
  private ProgressBar _progress;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(BatchRep.class);

  }
