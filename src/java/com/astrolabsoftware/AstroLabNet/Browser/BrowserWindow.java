package com.astrolabsoftware.AstroLabNet.Browser;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.Utils.StringFile;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.Init;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;

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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Orientation;
import javafx.collections.ObservableList; 
import javafx.event.ActionEvent;

// Java
import java.util.Map;
import java.util.HashMap;
import java.io.StringWriter;
import java.io.PrintWriter;

// Bean Shell
import bsh.Interpreter;
import bsh.util.JConsole;
import bsh.EvalError;

// Log4J
import org.apache.log4j.Logger;

/** <code>BrowserWindow</code> is the root browser for the
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
    Init.init(_console.interpreter());
    setupGUI(stage);
    readActions();
    }
    
  /** Fill the pre-defined {@link Action}s. */
  public void readActions() {
    addAction("Test", "1+1", Language.PYTHON);
    String ext;
    Language lang = Language.PYTHON;
    for (String actionTxt : new String[] {"pi.py",
                                          "pi.scala"}) {
      try {
        ext = actionTxt.substring(actionTxt.lastIndexOf(".") + 1);
        switch (ext) { // TBD: put into Language
          case "py":
            lang = Language.PYTHON;
            break;
          case "scala":
            lang = Language.SCALA;
            break;
           case "r":
            lang = Language.R;
            break;
          case "sql":
            lang = Language.SQL;
            break;
          default:
            log.error("Unknown extension " + ext + ", supposing Python");
          } 
        addAction("pi", new StringResource("com/astrolabsoftware/AstroLabNet/DB/Actions/" + actionTxt).toString(), lang);
        }
      catch (AstroLabNetException e) {
        log.error("Cannot load Action from " + actionTxt, e);
        }
      }
    }
    
  /** Create GUI.
    * @param stage The GUI {@link Stage}. */
  private void setupGUI(Stage stage) {
    // About
    Label about = new AboutLabel();       
    // Exit
    Button exit = new SimpleButton("Exit", Images.EXIT, "Exit", new ExitHandler(this)); 
    // Menu = About + Exit
    HBox menu = new HBox();    
    menu.setSpacing(5);
    ObservableList menuList = menu.getChildren();  
    menuList.addAll(about, exit);           
    // Tree
    TreeItem<ElementRep> root = new TreeItem<>(new ElementRep(new Element("/"), this));
    root.setExpanded(true);
    TreeView<ElementRep> tree = new TreeView<>(root);
    new ToolTipper(tree, "Right-click on elements will show available operations");
    TreeCellCallback callback = new TreeCellCallback();
    tree.setCellFactory(callback);
    root.getChildren().addAll(_servers,
                              _data,
                              _dataSources,
                              _channels,
                              _actions,
                              _tasks);
    // Help
    String helpText = "";
    for (String helpPage : new String[] {"Browser/Components/",
                                         "DB/Server",
                                         "DB/Session",
                                         "DB/Action",
                                         "DB/Task"}) {
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
    
  /** Add {@link ServerRep}.
    * @param name     The {@link ServerRep} name.
    * @param urlLivy  The url of the <em>Livy</em> server. Should not be <tt>null</tt>.
    * @param urlSpark The url of the <em>Spark</em> server. May be <tt>null</tt>. */
  public void addServer(String name,
                        String urlLivy,
                        String urlSpark) {
    if (urlLivy == null) {
      log.warn("No Livy server defined for " + name);
      return;
      }
    ServerRep serverRep = ServerRep.create(new Server(name, urlLivy, urlSpark), this);
    TreeItem<ElementRep> serverItem = serverRep.item();
    _servers.getChildren().add(serverItem);
    if (serverRep.urlLivy() == null) {
      log.warn("Livy url for " + serverRep.name() + " is not defined !");
      }
    else {
      WebView viewLivy = new WebView();
      WebEngine engineLivy = viewLivy.getEngine();
      engineLivy.load(serverRep.urlLivy());
      addTab(viewLivy, serverRep.name() + " : Livy : " + serverRep.urlLivy(), Images.LIVY);
      serverRep.updateSessions();
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
    }
    
  /** Add {@link DataRep}.
    * @param name The {@link DataRep} name. */
  public void addData(String name) {
    DataRep dataRep = new DataRep(new Data(name), this);
    log.info("Adding Data " + dataRep);
    _data.getChildren().add(dataRep.item());
    }
    
  /** Add {@link DataSourceRep}.
    * @param name The {@link DataSourceRep} name. */
  public void addDataSource(String name) {
    DataSourceRep dataSourceRep = new DataSourceRep(new DataSource(name), this);
    log.info("Adding Data Source " + dataSourceRep);
    _dataSources.getChildren().add(dataSourceRep.item());
    }

  /** Add {@link ChannelRep}.
    * @param name The {@link ChannelRep} name. */
  public void addChannel(String name) {
    ChannelRep channelRep = new ChannelRep(new Channel(name), this);
    log.info("Adding Channel " + channelRep);
    _channels.getChildren().add(channelRep.item());
    }
    
  /** Add {@link ActionRep}.
    * @param name     The {@link ActionRep} name.
    * @param cmd      The command to execute.
    * @param language The {@link Language} of the command. */
  public void addAction(String   name,
                        String   cmd,
                        Language language) {
    ActionRep actionRep = new ActionRep(new Action(name, cmd, language), this);
    log.info("Adding Action " + actionRep);
    _actions.getChildren().add(actionRep.item());
    }
    
  /** Add {@link TaskRep}.
    * @param name    The {@link TaskRep} name.
    * @param session The hosting {@link Session}.
    * @param id      The statement id. */
  public void addTask(String  name,
                      Session session,
                      int     id) {
    TaskRep taskRep = TaskRep.create(new Task(name, session, id), this);
    TreeItem<ElementRep> taskItem = taskRep.item();
    _tasks.getChildren().add(taskItem);
    }
 
  /** Register the {@link SessionRep} command, so that it can be filled
    * by {@link ActionRep}.
    * @param sessionRep The related {@link SessionRep}.
    * @param tab        The {@link Tab} containing the {@link SessionRep}. */
  public void registerSessionTab(SessionRep sessionRep,
                                 Tab        tab) {
    _sessionTabs.put(sessionRep, tab);
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
  
  /** Close. */
  public void close() {
    System.exit(0);
    }  
    
  private TreeItem<ElementRep> _servers     = new TreeItem<>(new ElementRep(new Element("Servers"),       this));
  private TreeItem<ElementRep> _data        = new TreeItem<>(new ElementRep(new Element("Data"),          this));
  private TreeItem<ElementRep> _dataSources = new TreeItem<>(new ElementRep(new Element("Data Sources"),  this));
  private TreeItem<ElementRep> _channels    = new TreeItem<>(new ElementRep(new Element("Data Channels"), this));
  private TreeItem<ElementRep> _actions     = new TreeItem<>(new ElementRep(new Element("Actions"),       this));
  private TreeItem<ElementRep> _tasks       = new TreeItem<>(new ElementRep(new Element("Tasks"),         this));
  
  private Map<SessionRep, Tab> _sessionTabs = new HashMap<>();
  
  private static Console _console;
  
  private TabPane _results = new TabPane();
    
  /** Logging . */
  private static Logger log = Logger.getLogger(BrowserWindow.class);
  
  }
