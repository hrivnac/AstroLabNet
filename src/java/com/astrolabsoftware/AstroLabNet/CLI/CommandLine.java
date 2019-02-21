package com.astrolabsoftware.AstroLabNet.CLI;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.Utils.StringFile;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.Init;
import com.astrolabsoftware.AstroLabNet.Utils.Info;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Core.Interacter;
import com.astrolabsoftware.AstroLabNet.Core.InteracterHelper;

// Bean Shell
import bsh.util.JConsole;
import bsh.Interpreter;
import bsh.EvalError;

// Java
import java.util.List;
import java.util.ArrayList;
import java.io.InputStreamReader;

// Log4J
import org.apache.log4j.Logger;

/** Simple Command Line.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class CommandLine implements Interacter {

  /** Start {@link Interpreter} and run forever. */
  public CommandLine() {
    Interpreter interpreter = new Interpreter(new InputStreamReader(System.in), System.out, System.err, true);
    try {
      interpreter.set("w", this);
      }
    catch (EvalError e) {
      log.error("Can't set CommandLine reference", e);
      }
    interpreter.print("Welcome to AstroLabNet " + Info.release() + "\n");
    interpreter.print("https://astrolabsoftware.github.io\n");
    Init.init(interpreter);
    new Thread(interpreter).start();
    readActions();
    }
    
  @Override
  public void readActions() {
    InteracterHelper.readActions(this);
    }

  @Override
  public void addServer(String name,
                        String urlLivy,
                        String urlSpark) {
    if (urlLivy == null) {
      log.warn("No Livy server defined for " + name);
      return;
      }
    Server server = new Server(name, urlLivy, urlSpark);
    log.info("Adding: " + server);
    _servers.add(server);
    }
    
  @Override
  public void addAction(String   name,
                        String   cmd,
                        Language language) {
    Action action = new Action(name, cmd, language);
    log.info("Adding: " + action);
    _actions.add(action);
    }
    
  @Override
  public void addData(String name) {
    Data data = new Data(name);
    log.info("Adding: " + data);
    _datas.add(data);
    }
    
  @Override
  public void addDataSource(String name) {
    DataSource dataSource = new DataSource(name);
    log.info("Adding: " + dataSource);
    _dataSources.add(dataSource);
    }
    
  @Override
  public void addDataChannel(String name) {
    DataChannel dataChannel = new DataChannel(name);
    log.info("Adding: " + dataChannel);
    _dataChannels.add(dataChannel);
    }
    
  @Override
  public void addTask(String  name,
                      Session session,
                      int     id) {
    Task task = new Task(name, session, id);
    log.info("Adding: " + task);
    _tasks.add(task);
    }

  private List<Server>      _servers      = new ArrayList<>();
  private List<Action>      _actions      = new ArrayList<>();
  private List<Data>        _datas        = new ArrayList<>();
  private List<DataSource>  _dataSources  = new ArrayList<>();
  private List<DataChannel> _dataChannels = new ArrayList<>();
  private List<Task>        _tasks        = new ArrayList<>();
 
  /** Logging . */
  private static Logger log = Logger.getLogger(CommandLine.class);
   
  }
    
    