package com.astrolabsoftware.AstroLabNet.Browser;

import com.astrolabsoftware.AstroLabNet.Livyser.LivyRI;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.Utils.Init;
import com.astrolabsoftware.AstroLabNet.Utils.StringFile;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;
import com.astrolabsoftware.AstroLabNet.DB.*;

// Swing
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

// JavaFX
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField; 
import javafx.scene.layout.BorderPane; 
import javafx.scene.layout.HBox; 
import javafx.scene.layout.VBox; 
import javafx.collections.ObservableList; 
import javafx.geometry.Insets; 
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.text.Text; 
import javafx.scene.text.TextAlignment; 
import javafx.scene.text.TextFlow;
import javafx.geometry.Pos;

// Java
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.Enumeration;

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
    Init.init();
    }
  
  @Override
  public void start(Stage stage) {
    setupContent();
    setupGUI(stage);
    Thread t  = new Thread(_interpreter);
    t.start();
    }
    
  /** TBD */
  private void setupContent() {
    // Console
    _console = new Console();
    _console.setWaitFeedback(true);
    _interpreter = new Interpreter(_console);
    try {
      _interpreter.set("w", this);
      _interpreter.eval("import com.astrolabsoftware.AstroLabNet.DB.*");
      _interpreter.eval(new StringFile("init.bsh").toString());
      }
    catch (EvalError e) {
      reportException("Can't evaluate standard BeanShell expression", e, log);
      }
    // Servers & Sessions
    for (TreeItem<Element> serverItem : _servers.getChildren()) {
      if (serverItem.getValue() instanceof Server) {
        Server server = (Server)(serverItem.getValue());
        WebView view = new WebView();
        WebEngine engine = view.getEngine();
        engine.load(server.url());
        Tab tab = new Tab();
        tab.setText(server.name() + " : " + server.url());
        tab.setContent(view);   
        _results.getTabs().addAll(tab);
        for (int id : _livy.getSessions(server.url())) {
          serverItem.getChildren().add(new TreeItem<Element>(new Session("Session", id)));
          }
        }
      }
    }
    
  /** TBD */
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
    TreeItem<Element> root = new TreeItem<>(new Element("/"));
    root.setExpanded(true);
    TreeView<Element> tree = new TreeView<>(root);
    root.getChildren().addAll(_servers,
                              _data,
                              _dataSources,
                              _channels,
                              _jobs);
    // Help
    Text helpText = new Text("HELP !");
    try {
      helpText = new Text(new StringResource("com/astrolabsoftware/AstroLabNet/Browser/Components/Help.txt").toString());
      }
    catch (AstroLabNetException e) {
      log.error("Cannot load help page !", e);
      }
    TextFlow help = new TextFlow(); 
    help.setTextAlignment(TextAlignment.JUSTIFY); 
    //help.setPrefSize(600, 300); 
    help.setLineSpacing(5.0); 
    ObservableList helpList = help.getChildren();       
    helpList.addAll(helpText);              
    // Results = Help
    Tab tab = new Tab();
    tab.setText("Help");
    tab.setContent(help);    
    _results.getSelectionModel().select(0);
    _results.getTabs().addAll(tab);  
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
    stage.setTitle("AstroLabNet Browser"); 
    stage.setScene(scene);          
    stage.show(); 
    }
    
  private void createSwingContent(SwingNode swingNode,
                                  Console   component) {
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
    
  /** TBD */
  public void addServer(String name,
                        String url) {
    log.info("Adding Livy server " + name + " (" + url + ")");
    Server server = new Server(name, url);
    _servers.getChildren().add(new TreeItem<Element>(server));
    }
 
  /** TBD */
  public void addData(String name) {
    log.info("Adding Data " + name);
    Data data = new Data(name);
    _data.getChildren().add(new TreeItem<Element>(data));
    }
    
  /** TBD */
  public void addDataSource(String name) {
    log.info("Adding Data Source " + name);
    DataSource dataSource = new DataSource(name);
    _dataSources.getChildren().add(new TreeItem<Element>(dataSource));
    }

  /** TBD */
  public void addChannel(String name) {
    log.info("Adding Channel " + name);
    Channel channel = new Channel(name);
    _channels.getChildren().add(new TreeItem<Element>(channel));
    }
    
  /** TBD */
  public void addJob(String name) {
    log.info("Adding Job " + name);
    Job job = new Job(name);
    _jobs.getChildren().add(new TreeItem<Element>(job));
    }
 
  /** Close. */
  public void close() {
    System.exit(0);
    }  

  private LivyRI _livy = new LivyRI();
    
  private TreeItem<Element> _servers     = new TreeItem<>(new Element("Servers"));
  private TreeItem<Element> _data        = new TreeItem<>(new Element("Data"));
  private TreeItem<Element> _dataSources = new TreeItem<>(new Element("Data Sources"));
  private TreeItem<Element> _channels    = new TreeItem<>(new Element("Data Channels"));
  private TreeItem<Element> _jobs        = new TreeItem<>(new Element("Jobs"));
  
  private static Console _console;

  private Interpreter _interpreter;
  
  private TabPane _results = new TabPane();
    
  /** Logging . */
  private static Logger log = Logger.getLogger(BrowserWindow.class);

  }
