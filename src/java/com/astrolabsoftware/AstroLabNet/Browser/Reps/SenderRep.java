package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.ReadJARHandler;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.SplitPane;
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

// Java
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
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

/** <code>SenderRep</code> is {@link BrowserWindow} representation of {@link Sender}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class SenderRep extends ElementRep {
  
  /** Create new SenderRep as a <em>Singleton</em>.
    * @param sender The original {@link Sender}.
    * @param browser The {@link BrowserWindow}. */
  public static SenderRep create(Sender        sender,
                                  BrowserWindow  browser) {
    SenderRep senderRep = _senderReps.get(sender.toString());
    if (senderRep == null) {
      senderRep = new SenderRep(sender, browser);
      log.info("Adding Sender " + senderRep);
      _senderReps.put(sender.toString(), senderRep);
      }
    return senderRep;
    }
  
  private static Map<String, SenderRep> _senderReps = new HashMap<>();  
  
  /** Create new SenderRep.
    * @param sender   The represented {@link Sender}.
    * @param browser   The {@link BrowserWindow}. */
  public SenderRep(Sender sender,
                    BrowserWindow browser) {
    super(sender, browser, Images.SENDER);
    }
    
  /** Give the keeping {@link ServerRep}.
    * @return The keeping {@link ServerRep}. */
  public ServerRep serverRep() {
    return ServerRep.create(server(), browser());
    }
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem execute = new MenuItem("Prepare for SEnding",  Images.icon(Images.SENDER));
    execute.setOnAction(new SenderEventHandler(this));
    menuItems.add(execute);
    return menuItems;
    }
        
  /** Add {@link Tab} of this SenderRep. */
  public void addTab() {
    // Desc
    Label desc = new Label("Job:");
    // JAR
    Button jar = new SimpleButton("JAR file", Images.JAR, "Load JAR file", new ReadJARHandler(this));
    // File
    _file = new TextField();
    _file.setPrefColumnCount(50);
    // ClassNameSender 
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
    cmdBox.getChildren().addAll(desc, jobBox , buttonBox);
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
    SenderRep senderRep = this;
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        int id = serverRep().livy().sendJob("local:" + _file.getText(), _className.getText(), Integer.MAX_VALUE, 1);
        browser().command().addBatch(serverRep().name() + "/" + id, senderRep.sender(), id);
        resultText.getChildren().add(new Text("Job send\n\n"));
        }
      });
    setResultRef(resultText);
    Tab tab = browser().addTab(pane, toString(), Images.SESSION);
    browser().registerSenderTab(this, tab);
    }

  /** Set reference to result.
    * To be filled once available.
    * @param resultRef The result {@link TextFlow} shown in the {@link SenderRep} tab.*/
  public void setResultRef(TextFlow resultRef) {
    _resultRef = resultRef;
    }
    
  /** Set result into result reference on the {@link SenderRep} tab.
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
    
  /** Give the SenderRep keeping {@link Server}.
    * @return The SenderRep keeping {@link Server}. */
  public Server server() {
    return sender().server();
    }
 
  /** Give the referenced {@link Sender}.
    * @return The referenced {@link Sender}. */
  public Sender sender() {
    return (Sender)element();
    }
   
  @Override
  public String toString() {
    return sender().toString();
    }
    
  private TextField _file;  
    
  private TextField _className;  
  
  private TextFlow _resultRef;
  
  private ProgressBar _progress;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(SenderRep.class);

  }
