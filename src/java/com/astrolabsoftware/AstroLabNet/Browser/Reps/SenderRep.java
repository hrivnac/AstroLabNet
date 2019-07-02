package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.DB.Sender;
import com.astrolabsoftware.AstroLabNet.DB.Server;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers.SenderEventHandler;
import com.astrolabsoftware.AstroLabNet.Browser.Components.HeaderLabel;
import com.astrolabsoftware.AstroLabNet.Browser.Components.SimpleButton;
import com.astrolabsoftware.AstroLabNet.Browser.Components.Images;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;

// JavaFX
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Java
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;

// Java
import java.util.Optional;

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
    MenuItem execute = new MenuItem("Prepare for Sending",  Images.icon(Images.SENDER));
    execute.setOnAction(new SenderEventHandler(this));
    menuItems.add(execute);
    return menuItems;
    }
        
  /** Add {@link Tab} of this SenderRep. */
  public void addTab() {
    // Desc
    Label desc = new HeaderLabel(toString(), "Send Job to Spark");
    // Load
    Button load = new SimpleButton("JAR/PY file", Images.JAR, "Load JAR or PY file");
    // File
    _file = new TextField();
    _file.setPrefColumnCount(50);
    // JobBox1 = Load + File
    HBox jobBox1 = new HBox(10);
    jobBox1.setSpacing(5);
    jobBox1.setAlignment(Pos.CENTER);
    jobBox1.getChildren().addAll(load, _file);
    // JobBox2 = ClassName
    HBox jobBox2 = jobBox("Class Name", _className);
    // JobBox3 = Args
    HBox jobBox3 = jobBox("Args", _args);   
    // DriverMemoryLabel
    Label driverMemoryLabel = new Label("Driver Memory:");
    // DriverMemory
    _driverMemory = new TextField();
    _driverMemory.setPrefColumnCount(10);
    // DriverCoresLabel
    Label driverCoresLabel = new Label("Driver Cores:");
    // DriverCores
    _driverCores = new Spinner<Integer>();
    SpinnerValueFactory<Integer> driverCoresFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 3);
    _driverCores.setValueFactory(driverCoresFactory);
    // JobBox4 = DriverMemoryLabel + DriverMemory + DriverCoresLabel + DriverCores
    HBox jobBox4 = new HBox(10);
    jobBox4.setSpacing(5);
    jobBox4.setAlignment(Pos.CENTER);
    jobBox4.getChildren().addAll(driverMemoryLabel, _driverMemory, driverCoresLabel, _driverCores);
    // ExecutorMemoryLabel
    Label executorMemoryLabel = new Label("Executor Memory:");
    // ExecutorMemory
    _executorMemory = new TextField();
    _executorMemory.setPrefColumnCount(10);
    // ExecutorCoresLabel
    Label executorCoresLabel = new Label("Executor Cores:");
    // ExecutorCores
    _executorCores = new Spinner<Integer>();
    SpinnerValueFactory<Integer> executorCoresFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 3);
    _executorCores.setValueFactory(executorCoresFactory);
    // JobBox5 = ExecutorMemoryLabel + ExecutorMemory + ExecutorCoresLabel + ExecutorCores
    HBox jobBox5 = new HBox(10);
    jobBox5.setSpacing(5);
    jobBox5.setAlignment(Pos.CENTER);
    jobBox5.getChildren().addAll(executorMemoryLabel, _executorMemory, executorCoresLabel, _executorCores);   
    // NumExecutorsLabel
    Label numExecutorsLabel = new Label("Executors:");
    // NumExecutors
    _numExecutors = new Spinner<Integer>();
    SpinnerValueFactory<Integer> numExecutorsFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 3);
    _numExecutors.setValueFactory(numExecutorsFactory);
    // JobBox6 = NumExecutorsLabel + NumExecutors
    HBox jobBox6 = new HBox(10);
    jobBox6.setSpacing(5);
    jobBox6.setAlignment(Pos.CENTER);
    jobBox6.getChildren().addAll(numExecutorsLabel, _numExecutors);
    // JobBox7 = Jars
    HBox jobBox7 = jobBox("Jars", _jars);  
    // JobBox8 = PyFiles
    HBox jobBox8 = jobBox("PyFiles", _pyFiles); 
    // JobBox9 = Files
    HBox jobBox9 = jobBox("Files", _files); 
    // JobBox10 = Archives
    HBox jobBox10 = jobBox("Archives", _archives);  
    // JobBox11 = Queue
    HBox jobBox11 = jobBox("Queue", _queue);  
    // JobBox12 = Conf
    HBox jobBox12 = jobBox("Conf", _conf); 
    // JobBox13 = ProxyUser
    HBox jobBox13 = jobBox("ProxyUser", _proxyUser);  
    // JobBox = JobBox1-13
    VBox jobBox = new VBox(10);
    jobBox.setSpacing(5);
    jobBox.setAlignment(Pos.CENTER);
    jobBox.getChildren().addAll(jobBox1, jobBox2, jobBox3, jobBox4, jobBox5, jobBox6, jobBox7, jobBox8, jobBox9, jobBox10, jobBox11, jobBox12, jobBox13);
    // Progress
    _progress = new ProgressBar(0);
    // Send
    Button send = new SimpleButton("Send", "Send Job to Spark");
    // Record
    Button record = new SimpleButton("Record", "Record Job");
    // ButtonBox = Progress + Send + Record
    HBox sendBox = new HBox(10);
    sendBox.setSpacing(5);
    sendBox.setAlignment(Pos.CENTER);
    sendBox.getChildren().addAll(_progress, send, record);
    // CmdBox = Desc + JobBox + SendBox 
    VBox cmdBox = new VBox();
    cmdBox.setSpacing(5);
    cmdBox.setAlignment(Pos.CENTER);
    cmdBox.getChildren().addAll(desc, jobBox , sendBox);
    // ResultText
    TextFlow resultText = new TextFlow(); 
    Text result0 = new Text("Fill in or select Action\n\n");
    result0.setFill(Color.DARKGREEN);
    resultText.getChildren().add(result0);
    // ScrollPane = ResultText
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setFitToWidth(true);
    scrollPane.vvalueProperty().bind(resultText.heightProperty());
    scrollPane.setContent(resultText);
    // Pane = Desc + CmdBox + ButtonBox + ScrollPane
    SplitPane pane = new SplitPane();
    pane.setDividerPositions(0.5);
    pane.setOrientation(Orientation.VERTICAL);
    pane.getItems().addAll(cmdBox, scrollPane);
    // Actions
    SenderRep senderRep = this;
    load.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        Stage dialog = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open JAR File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JAR/PY File", "*.jar", "*.py"));
        dialog.initStyle(StageStyle.UTILITY);
        File selectedFile = fileChooser.showOpenDialog(dialog);
        if (selectedFile != null) {
          setFile(selectedFile);
          }
        }
      });
    send.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        int id = serverRep().livy().sendJob(_file.getText(),
                                            _className.getText(),
                                            _args.getText(),
                                            _driverMemory.getText(),
                                            _driverCores.getValue(),
                                            _executorMemory.getText(),
                                            _executorCores.getValue(),
                                            _numExecutors.getValue(), 
                                            _jars.getText(),          
                                            _pyFiles.getText(),       
                                            _files.getText(),         
                                            _archives.getText(),      
                                            _queue.getText(),         
                                            "TBD name",               
                                            _conf.getText(),
                                            _proxyUser.getText(),
                                            Integer.MAX_VALUE,
                                            1);
        browser().command().addBatch(serverRep().name() + "/" + id, senderRep.sender(), id);
        resultText.getChildren().add(new Text("Job send\n\n"));
        }
      });
    record.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        TextInputDialog dialog = new TextInputDialog("MyName");
        dialog.setTitle("New Job Name");
        dialog.setHeaderText("The name of new Job");
        dialog.setContentText("Please enter the name:");
        Optional<String> answer = dialog.showAndWait();
        answer.ifPresent(name -> browser().command().addJob(name,
                                                            _file.getText(),
                                                            _className.getText(),
                                                            _args.getText(),
                                                            _driverMemory.getText(),
                                                            _driverCores.getValue(),
                                                            _executorMemory.getText(),
                                                            _executorCores.getValue(),
                                                            _numExecutors.getValue(),
                                                            _jars.getText(),
                                                            _pyFiles.getText(),
                                                            _files.getText(),
                                                            _archives.getText(),
                                                            _queue.getText(),
                                                            _conf.getText(),
                                                            _proxyUser.getText()).setNew());
        }
      });
    // Set
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
        //if (attrName == Attribhttps://stackoverflow.com/questions/7498016/reusing-code-in-overloaded-constructorsutes.Name.MAIN_CLASS) {
        if (attrName.toString().trim().equals("Main-Class")) {
          _className.setText(attrValue);
          }
        }
      }
    catch (IOException e) {
      log.error("Cannot gethttps://stackoverflow.com/questions/7498016/reusing-code-in-overloaded-constructors " + file, e);
      }
    }
    
  /** TBD */
  public void fill(String file,
                   String className,
                   String args,
                   String driverMemory,
                   int    driverCores,
                   String executorMemory,
                   int    executorCores,                  
                   int    numExecutors,
                   String jars,
                   String pyFiles,
                   String files,
                   String archives,
                   String queue,
                   String conf,
             String proxyUser) {
    _file.setText(file);
    _className.setText(className);
    _args.setText(args);
    _driverMemory.setText(driverMemory);
    _driverCores.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, driverCores));
    _executorMemory.setText(executorMemory);
    _executorCores.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, driverCores));
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
    
  /** TBD */
  private HBox jobBox(String name,
                      TextField field) {
    HBox jobBox = new HBox(10);
    Label label = new Label(name + ":");
    field.setPrefColumnCount(50);
    jobBox = new HBox(10);
    jobBox.setSpacing(5);
    jobBox.setAlignment(Pos.CENTER);
    jobBox.getChildren().addAll(label, field);
    return jobBox;
    }
   
  @Override
  public String toString() {
    return sender().toString();
    }
    
  private TextField        _file = new TextField();  
    
  private TextField        _className = new TextField();  
  
  private TextField        _args = new TextField();
  
  private TextField        _driverMemory;
  
  private Spinner<Integer> _driverCores;
  
  private TextField        _executorMemory;
  
  private Spinner<Integer> _executorCores;
  
  private Spinner<Integer> _numExecutors;
  
  private TextField        _jars = new TextField();
                           
  private TextField        _pyFiles = new TextField();
                           
  private TextField        _files = new TextField();
                           
  private TextField        _archives = new TextField();
                           
  private TextField        _queue = new TextField();
                           
  private TextField        _conf = new TextField();
                           
  private TextField        _proxyUser = new TextField();
   
  private TextFlow         _resultRef;
  
  private ProgressBar      _progress;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(SenderRep.class);

  }
