package com.astrolabsoftware.AstroLabNet.Browser;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// Swing
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

// AWT
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

// JavaFX
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane; 
import javafx.scene.layout.HBox; 
import javafx.scene.layout.VBox; 
import javafx.scene.control.SplitPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Orientation;
import javafx.collections.ObservableList; 

// Java
import java.util.Map;
import java.util.HashMap;

// Log4J
import org.apache.log4j.Logger;

/** <code>BrowserWindow</code> is the root browser window for the
  * <em>AstroLabNet</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class BrowserWindow extends Application {

  @Override
  public void init() {
    }
  
  @Override
  public void start(Stage stage) {
    _console = new Console(this);
    _command = new BrowserCommand(this);
    _command.setupInterpreter(_console.interpreter());
    setupGUI(stage);
    }
    
  /** Create GUI.
    * @param stage The GUI {@link Stage}. */
  private void setupGUI(Stage stage) {
    // About
    Label about = new AboutLabel();       
    // Script
    Button script = new SimpleButton("Script", Images.SCRIPT, "Read BSH Script", new ReadScriptHandler(this));
    // Exit
    Button exit = new SimpleButton("Exit", Images.EXIT, "Exit", new ExitHandler(this)); 
    // Menu = About + Script + Exit
    HBox menu = new HBox();    
    menu.setSpacing(5);
    ObservableList menuList = menu.getChildren();  
    menuList.addAll(about, script, exit);           
    // Tree
    TreeItem<ElementRep> root = new TreeItem<>(new ElementRep(new Element("/"), this));
    root.setExpanded(true);
    TreeView<ElementRep> tree = new TreeView<>(root);
    new ToolTipper(tree, "Right-click on elements will show available operations");
    TreeCellCallback callback = new TreeCellCallback();
    tree.setCellFactory(callback);
    root.getChildren().addAll(_command.serverReps(),
                              _command.dataReps(),
                              _command.channelReps(),
                              _command.actionReps(),
                              _command.jobReps(),
                              _command.taskReps(),
                              _command.batchReps(),
                              _command.searchReps());
    // Help
    String helpText = "";
    for (String helpPage : new String[] {"Browser/Components/",
                                         "DB/Server",
                                         "DB/Session",
                                         "DB/Batch",
                                         "DB/Action",
                                         "DB/Job",
                                         "DB/Task",
                                         "DB/Batch",
                                         "DB/Search"}) {
      try {
        helpText += new StringResource("com/astrolabsoftware/AstroLabNet/" + helpPage + "Help.txt").toString();
        }
      catch (AstroLabNetException e) {
        log.error("Cannot load help page " + helpPage, e);
        }
      helpText += "<hr/>";
      }
    WebView help = new WebView();
    WebEngine engine = help.getEngine();
    engine.loadContent(helpText);
    // Results = Help
    addTab(help, "Help", Images.HELP);
    // Console
    SwingNode console = new SwingNode();
    createSwingContent(console, _console);
    //console.setContent(_console);
    // Center = Results + Console
    SplitPane center = new SplitPane();
    center.setDividerPositions(0.8);
    center.setOrientation(Orientation.VERTICAL);
    center.getItems().addAll(_results, console);
    // Content = Tree + Center
    SplitPane content = new SplitPane();
    content.setDividerPositions(0.2);
    content.setOrientation(Orientation.HORIZONTAL);
    content.getItems().addAll(tree, center);
    // All = Menu + Content
    BorderPane all = new BorderPane();
    all.setTop(menu);
    all.setCenter(content);
    // Scene
    Scene scene = new Scene(all);
    scene.getStylesheets().add("com/astrolabsoftware/AstroLabNet/Browser/BrowserWindow.css"); 
    stage.setTitle("AstroLabNet Browser"); 
    stage.setScene(scene);          
    stage.show(); 
    }
    
  /** Wrap <em>Swing</em> {@link JComponent} in <em>JavaFX</em>.
    * @param swingNode The wrapping {@link SwingNode}.
    * @param component The wrapped <em>Swing</em> {@link JComponent}. */
  private void createSwingContent(SwingNode  swingNode,
                                  JComponent component) {
    SwingUtilities.invokeLater(new Runnable() {
    //Platform.runLater(new Runnable() {
      @Override
      public void run() {
        swingNode.setContent(component);      
        }                                                                             
      });
    }
   
  /** Wrap <em>Swing</em> {@link JComponent} in <em>JavaFX</em>.
    * @param swingNode The wrapping {@link SwingNode}.
    * @param component The wrapped <em>Swing</em> {@link JComponent}. */
  private void createSwingContentNew(SwingNode  swingNode,
                                  JComponent component) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        swingNode.setContent(component);
        component.addFocusListener(new FocusListener() {
          @Override
          public void focusGained(FocusEvent e) {
            Platform.runLater(new Runnable() {
              @Override
              public void run() {} 
              });
            }
          @Override
          public void focusLost(FocusEvent e) {}
          });
        }
      });
    }
    
  @Override
  public void stop() {
    }
    
  /** Add new {@link Tab}.
    * @param node  The {@link Node} to embed in the {@link Tab}.
    * @param title The {@link Tab} title.
    * @param icon  The {@link Tab} title icon.
    * @return      The created {@link Tab}. */
  public Tab addTab(Node node,
                    String title,
                    Image icon) {
    Tab tab = new Tab();
    tab.setText(title);
    if (icon != null) {
      tab.setGraphic(Images.icon(icon));
      }
    tab.setContent(node); 
    _results.getTabs().addAll(tab);
    _results.getSelectionModel().select(tab);
    return tab;
    }
    
  /** Show {@link Server} in the window.
    * @param serverRep The {@link ServerRep} to be shown. */
  public void showServer(ServerRep serverRep) {
    if (serverRep.urlLivy() == null) {
      log.warn("Livy url for " + serverRep.name() + " is not defined !");
      }
    else {
      WebView viewLivy = new WebView();
      WebEngine engineLivy = viewLivy.getEngine();
      engineLivy.load(serverRep.urlLivy());
      addTab(viewLivy, serverRep.name() + " : Livy : " + serverRep.urlLivy(), Images.LIVY);
      serverRep.updateSessions();
      serverRep.updateSenders();
      }
    if (serverRep.urlSpark() == null) {
      log.warn("Spark url for " + serverRep.name() + " is not defined !");
      }
    else {
      WebView viewSpark = new WebView();
      WebEngine engineSpark = viewSpark.getEngine();
      engineSpark.load(serverRep.urlSpark());
      addTab(viewSpark, serverRep.name() + " : Spark : " + serverRep.urlSpark(), Images.SPARK);
      }
    if (serverRep.urlHBase() == null) {
      log.warn("HBase url for " + serverRep.name() + " is not defined !");
      }
    else {
      WebView viewHBase = new WebView();
      WebEngine engineHBase = viewHBase.getEngine();
      engineHBase.load(serverRep.urlHBase() + "/status/cluster");
      addTab(viewHBase, serverRep.name() + " : HBase : " + serverRep.urlHBase(), Images.HBASE);
      }
    }
  
  /** Register the {@link SessionRep} command, so that it can be filled
    * by {@link ActionRep}.
    * @param sessionRep The related {@link SessionRep}.
    * @param tab        The {@link Tab} containing the {@link SessionRep}. */
  public void registerSessionTab(SessionRep sessionRep,
                                 Tab        tab) {
    _sessionTabs.put(sessionRep, tab);
    }
    
  /** Register the {@link SenderRep} command, so that it can be filled
    * by {@link JobRep}.
    * @param senderRep The related {@link SenderRep}.
    * @param tab       The {@link Tab} containing the {@link SenderRep}. */
  public void registerSenderTab(SenderRep senderRep,
                                Tab       tab) {
    _senderTabs.put(senderRep, tab);
    }
      
  /** Register the {@link SourceRep} command, so that it can be filled
    * by {@link ActionRep}.
    * @param sourceRep The related {@link SourceRep}.
    * @param tab        The {@link Tab} containing the {@link SourceRep}. */
  public void registerSourceTab(SourceRep sourceRep,
                                Tab       tab) {
    _sourceTabs.put(sourceRep, tab);
    }
    
  /** Set the {@link SessionRep} command text. To be called from {@link ActionRep}.
    * @param txt The text to fill in the {@link SessionRep} command. */
  public void setSessionCmd(String txt) {
    boolean done = false;
    for (Map.Entry<SessionRep, Tab> entry : _sessionTabs.entrySet()) {
      if (entry.getValue().isSelected()) {
        SplitPane pane = (SplitPane)(entry.getValue().getContent());
        VBox vbox = (VBox)(pane.getItems().get(0));
        TextArea actionTarget = (TextArea)(vbox.getChildren().get(1));
        actionTarget.setText(txt);
        done = true;
        break;
        }
      }
    if (!done) {
      log.error("No Session tab selected for Use");
      }
    }
    
  /** Set the {@link SenderRep} file and className. To be called from {@link JobRep}.
    * @param file      The jar file name to fill in the {@link BatchRep} command.
    * @param className The main className to fill in the {@link BatchRep} command.*/
  public void setSenderFile(String file,
                            String className) {
    boolean done = false;
    for (Map.Entry<SenderRep, Tab> entry : _senderTabs.entrySet()) {
      if (entry.getValue().isSelected()) {
        SplitPane pane = (SplitPane)(entry.getValue().getContent());
        VBox vbox = (VBox)(pane.getItems().get(0));
        HBox hbox = (HBox)(vbox.getChildren().get(1));
        TextField fileTarget       = (TextField)(hbox.getChildren().get(1));
        TextField classNameTarget = (TextField)(hbox.getChildren().get(2));
        fileTarget.setText(file);
        classNameTarget.setText(className);
        done = true;
        break;
        }
      }
    if (!done) {
      log.error("No Sender tab selected for Use");
      }
    }
    
  /** Give selected {@link SessionRep}.
    * @return The selected {@link SessionRep},
    *         <tt>null</tt> if no {@link SessionRep} is selected. */
  public SessionRep getSelectedSession() {
    for (Map.Entry<SessionRep, Tab> entry : _sessionTabs.entrySet()) {
      if (entry.getValue().isSelected()) {
        return entry.getKey();
        }
      }
    return null;
    }
    
  /** Give selected {@link SenderRep}.
    * @return The selected {@link SenderRep},
    *         <tt>null</tt> if no {@link SenderRep} is selected. */
  public SenderRep getSelectedSender() {
    for (Map.Entry<SenderRep, Tab> entry : _senderTabs.entrySet()) {
      if (entry.getValue().isSelected()) {
        return entry.getKey();
        }
      }
    return null;
    }
    
  /** Select {@link Tab} with requested {@link SessionRep}.
    * @param sessionRep The  requested {@link SessionRep} to be selected. */
  public void selectTab(SessionRep sessionRep) {
    Tab tab = _sessionTabs.get(sessionRep);
    // re-attach if closed
    if (tab.tabPaneProperty().getValue() == null) {
      _results.getTabs().addAll(tab);
      _results.getSelectionModel().select(tab);
      }
    _results.getSelectionModel().select(_sessionTabs.get(sessionRep));
    }
    
  /** Select {@link Tab} with requested {@link SenderRep}.
    * @param senderRep The  requested {@link SenderRep} to be selected. */
  public void selectTab(SenderRep senderRep) {
    Tab tab = _senderTabs.get(senderRep);
    // re-attach if closed
    if (tab.tabPaneProperty().getValue() == null) {
      _results.getTabs().addAll(tab);
      _results.getSelectionModel().select(tab);
      }
    _results.getSelectionModel().select(_senderTabs.get(senderRep));
    }
    
  /** Select {@link Tab} with requested {@link SourceRep}.
    * @param sourceRep The requested {@link SourceRep} to be selected. */
  public void selectTab(SourceRep sourceRep) {
    Tab tab = _sourceTabs.get(sourceRep);
    // re-attach if closed
    if (tab.tabPaneProperty().getValue() == null) {
      _results.getTabs().addAll(tab);
      _results.getSelectionModel().select(tab);
      }
    _results.getSelectionModel().select(_sourceTabs.get(sourceRep));
    }
  
  /** Close. */
  // TBD: merge with stop()
  public void close() {
    System.exit(0);
    }  
    
  /** Give embedded {@link Console}.
    * @return The embedded {@link Console}. */
  public Console console() {
    return _console;
    }
    
  /** Give associated {@link BrowserCommand}.
    * @return The associated {@link BrowserCommand}. */
  public BrowserCommand command() {
    return _command;
    }
    
  private BrowserCommand _command;  
  
  private Map<SessionRep, Tab> _sessionTabs = new HashMap<>();
  
  private Map<SenderRep,  Tab> _senderTabs  = new HashMap<>();

  private Map<SourceRep,  Tab> _sourceTabs  = new HashMap<>();
  
  private static Console _console;
  
  private TabPane _results = new TabPane();
    
  /** Logging . */
  private static Logger log = Logger.getLogger(BrowserWindow.class);
  
  }
