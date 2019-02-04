package com.astrolabsoftware.AstroLabNet.Browser;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.Utils.StringFile;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;

// Swing
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

// JavaFX
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane; 
import javafx.scene.layout.HBox; 
import javafx.scene.layout.VBox; 
import javafx.scene.layout.GridPane;
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
import javafx.geometry.Insets; 
import javafx.collections.ObservableList; 

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
    setupContent();
    setupGUI(stage);
    readActions();
    Thread t  = new Thread(_interpreter);
    t.start();
    }
    
  /** TBD */
  public void readActions() {
    addAction("Test", "1+1", Language.PYTHON);
    String ext;
    Language lang = Language.PYTHON;
    for (String actionTxt : new String[] {"pi.py",
                                          "pi.scala",
                                          "pi.r"}) {
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
    
  /** Connect to servers and populate GUI. */
  private void setupContent() {
    // Console
    _console = new Console();
    _console.setWaitFeedback(true);
    _interpreter = new Interpreter(_console);
    String init = "w.addServer(\"Local Host\", \"http://localhost:8998\", \"http://localhost:4040\")";
    try {
      init = new StringFile("init.bsh").toString();
      }
    catch (AstroLabNetException e) {
      log.warn("init.bsh file cannot be read, the default setup with Local Host server is used.");
      log.debug("init.bsh file cannot be read, the default setup with Local Host server is used.", e);
      }
    try {
      _interpreter.set("w", this);
      _interpreter.eval("import com.astrolabsoftware.AstroLabNet.DB.*");
      _interpreter.eval(init);
      }
    catch (EvalError e) {
      reportException("Can't evaluate standard BeanShell expression", e, log);
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
    menu.setSpacing(10);    
    menu.setMargin(about, new Insets(2, 2, 2, 2)); 
    menu.setMargin(exit,  new Insets(2, 2, 2, 2)); 
    ObservableList menuList = menu.getChildren();  
    menuList.addAll(about, exit);           
    // Tree
    TreeItem<Element> root = new TreeItem<>(new Element("/", this));
    root.setExpanded(true);
    TreeView<Element> tree = new TreeView<>(root);
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
    VBox center = new VBox();    
    center.setSpacing(10);  
    center.setMargin(_results, new Insets(2, 2, 2, 2)); 
    center.setMargin(console, new Insets(2, 2, 2, 2)); 
    ObservableList centerList = center.getChildren();  
    centerList.addAll(_results, console);  
    // Content = Menu + Tree + Center
    BorderPane content = new BorderPane();   
    content.setTop(menu); 
    content.setLeft(tree); 
    content.setCenter(center);
    // Scene
    Scene scene = new Scene(content);
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
      @Override
      public void run() {
        swingNode.setContent(component);        
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
    
  /** Add text to {@link JConsole}.
    * @param text The text to be added to  {@link JConsole}. */
  public static void setText(String text) {
    _console.print(text + "\n", new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 20), java.awt.Color.blue);
    }

  /** Add error text to {@link JConsole}.
    * @param text The error text to be added to  {@link JConsole}. */
  public static void setError(String text) {
    _console.print(text + "\n", new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 20), java.awt.Color.red);
    }

  /** Add simple text to {@link JConsole}.
    * @param text The simple text to be added to  {@link JConsole}. */
  public static void setText(String text, java.awt.Color color) {
    _console.print(text + "\n", new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 20), color);
    }

  /** Report {@link Throwable} to the logging system.
    * @param text The text to be reported.
    * @param e    The associated {@link Throwable}.
    * @param l    The {@link Logger} of the origin of the {@link Throwable} . */
  public static void reportException(String text, Throwable e, Logger l) {
    l.error(text + ", see AstroLabNet.log for details");
    StringWriter sw = new StringWriter();
    while (e != null) {
      e.printStackTrace(new PrintWriter(sw));
      e = e.getCause();
      }
    l.debug(sw.toString());
    }
    
  /** Add {@link Server}.
    * @param name     The {@link Server} name.
    * @param urlLivy  The url of the <em>Livy</em> server. May be <tt>null</tt>.
    * @param urlSpark The url of the <em>Spark</em> server. May be <tt>null</tt>. */
  public void addServer(String name,
                        String urlLivy,
                        String urlSpark) {
    Server server = new Server(name, this, urlLivy, urlSpark);
    log.info("Adding Server " + server);
    TreeItem<Element> serverItem = server.item();
    _servers.getChildren().add(serverItem);
    if (server.urlLivy() == null) {
      log.warn("Livy url for " + server.name() + " is not defined !");
      }
    else {
      WebView viewLivy = new WebView();
      WebEngine engineLivy = viewLivy.getEngine();
      engineLivy.load(server.urlLivy());
      addTab(viewLivy, server.name() + " : Livy : " + server.urlLivy(), Images.LIVY);
      server.updateSessions();
      }
    if (server.urlSpark() == null) {
      log.warn("Spark url for " + server.name() + " is not defined !");
      }
    else {
      WebView viewSpark = new WebView();
      WebEngine engineSpark = viewSpark.getEngine();
      engineSpark.load(server.urlSpark());
      addTab(viewSpark, server.name() + " : Spark : " + server.urlSpark(), Images.SPARK);
      }
    }
    
  /** Add {@link Data}.
    * @param name The {@link Data} name. */
  public void addData(String name) {
    Data data = new Data(name, this);
    log.info("Adding Data " + data);
    _data.getChildren().add(new TreeItem<Element>(data));
    }
    
  /** Add {@link DataSource}.
    * @param name The {@link DataSource} name. */
  public void addDataSource(String name) {
    DataSource dataSource = new DataSource(name, this);
    log.info("Adding Data Source " + dataSource);
    _dataSources.getChildren().add(new TreeItem<Element>(dataSource));
    }

  /** Add {@link Channel}.
    * @param name The {@link Channel} name. */
  public void addChannel(String name) {
    Channel channel = new Channel(name, this);
    log.info("Adding Channel " + channel);
    _channels.getChildren().add(new TreeItem<Element>(channel));
    }
    
  /** Add {@link Action}.
    * @param name     The {@link Action} name.
    * @param cmd      The command to execute.
    * @param language The {@link Language} of the command. */
  public void addAction(String   name,
                        String   cmd,
                        Language language) {
    Action action = new Action(name, this, cmd, language);
    log.info("Adding Action " + action);
    _actions.getChildren().add(action.item());
    }
    
  /** Add {@link Task}.
    * @param name    The {@link Task} name.
    * @param session The hosting {@link Session}.
    * @param id      The statement id. */
  public void addTask(String  name,
                      Session session,
                      int     id) {
    Task task = new Task(name, session, id, this);
    log.info("Adding Task " + task);
    _tasks.getChildren().add(new TreeItem<Element>(task));
    }
 
  /** Register the {@link Session} command, so that it can be filled
    * by {@link Action}.
    * @param session The related {@link Session}.
    * @param tab     The {@link Tab} containing the {@link Session}. */
  public void registerSessionTab(Session session,
                                 Tab     tab) {
    _sessionTabs.put(session, tab);
    }
    
  /** Set the {@link Session} command text. To be called from {@link Action}.
    * @param txt The text to fill in the {@link Session} command. */
  public void setSessionCmd(String txt) {
    boolean done = false;
    for (Map.Entry<Session, Tab> entry : _sessionTabs.entrySet()) {
      if (entry.getValue().isSelected()) {
        GridPane grid = (GridPane)(entry.getValue().getContent());
        TextArea actionTarget = (TextArea)(grid.getChildren().get(1));
        actionTarget.setText(txt);
        done = true;
        break;
        }
      }
    if (!done) {
      log.error("No Session tab selected for Use");
      }
    }
    
  /** TBD */
  public void selectTab(Session session) {
    _results.getSelectionModel().select(_sessionTabs.get(session));
  }
  
  /** Close. */
  public void close() {
    System.exit(0);
    }  
    
  private TreeItem<Element> _servers     = new TreeItem<>(new Element("Servers",       this));
  private TreeItem<Element> _data        = new TreeItem<>(new Element("Data",          this));
  private TreeItem<Element> _dataSources = new TreeItem<>(new Element("Data Sources",  this));
  private TreeItem<Element> _channels    = new TreeItem<>(new Element("Data Channels", this));
  private TreeItem<Element> _actions     = new TreeItem<>(new Element("Actions",       this));
  private TreeItem<Element> _tasks       = new TreeItem<>(new Element("Tasks",         this));
  
  private Map<Session, Tab> _sessionTabs = new HashMap<>();
  
  private static Console _console;

  private Interpreter _interpreter;
  
  private TabPane _results = new TabPane();
    
  /** Logging . */
  private static Logger log = Logger.getLogger(BrowserWindow.class);
  
  }
