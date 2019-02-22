package com.astrolabsoftware.AstroLabNet.Core;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.Utils.StringFile;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.Init;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Core.Interacter;

// Bean Shell
import bsh.Interpreter;
import bsh.util.JConsole;
import bsh.EvalError;

// Java
import java.util.List;
import java.util.ArrayList;

// Log4J
import org.apache.log4j.Logger;

/** Default methods for {@link Interacter}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public abstract class DefaultInteracter implements Interacter {
  
  @Override
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
     
  @Override
  public void setupInterpreter(Interpreter interpreter) {
    try {
      interpreter.eval("import com.astrolabsoftware.AstroLabNet.DB.*");
      interpreter.eval("import com.astrolabsoftware.AstroLabNet.Livyser.Language");
      interpreter.set("w", this);
      }
    catch (EvalError e) {
      log.error("Can't set CommandLine references", e);
      }
    String init = "";
    if (Init.source() != null) {
      log.info("Sourcing " + Init.source());
      try {
        init += new StringFile(Init.source()).toString();
        }
      catch (AstroLabNetException e) {
        log.warn(Init.source() + " file cannot be read, the default setup with Local Host server is used.");
        log.debug(Init.source() + " file cannot be read, the default setup with Local Host server is used.", e);
        }
      }
    log.info("Sourcing init.bsh");
    try {
      init += new StringFile("init.bsh").toString();
      }
    catch (AstroLabNetException e) {
      log.warn("init.bsh file cannot be read, the default setup with Local Host server is used.");
      log.debug("init.bsh file cannot be read, the default setup with Local Host server is used.", e);
      }
    if (init.equals("")) {
      log.warn("no suitable init bsh file found, the default setup with Local Host server will be used.");
      init = "w.addServer(\"Local Host\", \"http://localhost:8998\", \"http://localhost:4040\")";
      }
    try {
      interpreter.eval(init);
      }
    catch (EvalError e) {
      log.error("Can't evaluate standard BeanShell expression", e);
      }
    }    
    
  @Override
  public Server addServer(String name,
                          String urlLivy,
                          String urlSpark) {
    if (urlLivy == null) {
      log.warn("No Livy server defined for " + name);
      return null;
      }
    Server server = new Server(name, urlLivy, urlSpark);
    log.info("Adding Server: " + server);
    _servers.add(server);
    return server;
    }
    
  @Override
  public Action addAction(String   name,
                          String   cmd,
                          Language language) {
    Action action = new Action(name, cmd, language);
    log.info("Adding Action: " + action);
    _actions.add(action);
    return action;
    }
    
  @Override
  public Data addData(String name) {
    Data data = new Data(name);
    log.info("Adding Data: " + data);
    _datas.add(data);
    return data;
    }
    
  @Override
  public DataSource addDataSource(String name) {
    DataSource dataSource = new DataSource(name);
    log.info("Adding DataSource: " + dataSource);
    _dataSources.add(dataSource);
    return dataSource;
    }
    
  @Override
  public DataChannel addDataChannel(String name) {
    DataChannel dataChannel = new DataChannel(name);
    log.info("Adding DataChannel: " + dataChannel);
    _dataChannels.add(dataChannel);
    return dataChannel;
    }
    
  @Override
  public Task addTask(String  name,
                      Session session,
                      int     id) {
    Task task = new Task(name, session, id);
    log.info("Adding Task: " + task);
    _tasks.add(task);
    return task;
    }

  @Override
  public List<Server> servers() {
    return _servers;
    }
    
  @Override
  public List<Action> actions() {
    return _actions;
    }
    
  @Override
  public List<Data> datas() {
    return _datas;
    }
    
  @Override
  public List<DataSource> dataSources() {
    return _dataSources;
    }
    
  @Override
  public List<DataChannel> dataChannels() {
    return _dataChannels;
    }
    
  @Override
  public List<Task> tasks() {
    return _tasks;
    }
   
  private List<Server>      _servers      = new ArrayList<>();
  private List<Action>      _actions      = new ArrayList<>();
  private List<Data>        _datas        = new ArrayList<>();
  private List<DataSource>  _dataSources  = new ArrayList<>();
  private List<DataChannel> _dataChannels = new ArrayList<>();
  private List<Task>        _tasks        = new ArrayList<>();
  
  /** Logging . */
  private static Logger log = Logger.getLogger(DefaultInteracter.class);
  
  }
