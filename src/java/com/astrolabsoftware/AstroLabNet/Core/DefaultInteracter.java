package com.astrolabsoftware.AstroLabNet.Core;

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

// Bean Shell
import bsh.Interpreter;
import bsh.EvalError;

// org.json
import org.json.JSONObject;
import org.json.JSONArray;

// Java
import java.util.List;
import java.util.ArrayList;
import java.util.Base64;
import java.nio.file.FileSystems;

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
        addAction(actionTxt.substring(0, actionTxt.lastIndexOf(".")), new StringResource("com/astrolabsoftware/AstroLabNet/DB/Actions/" + actionTxt).toString(), lang);
        }
      catch (AstroLabNetException e) {
        log.error("Cannot load Action from " + actionTxt, e);
        }
      }
    }
    
  @Override
  public void readJobs() {
    addJob("Pi", "../lib/PiJob.jar", "com.astrolabsoftware.AstroLabNet.DB.Jobs.PiJob"); // TBD: should be automatic
    }
     
  @Override
  public void setupInterpreter(Interpreter interpreter) {
    _interpreter = interpreter;
    // Set global reference and imports
    try {
      interpreter.eval("import com.astrolabsoftware.AstroLabNet.DB.*");
      interpreter.eval("import com.astrolabsoftware.AstroLabNet.Livyser.Language");
      interpreter.set("w", this);
      }
    catch (EvalError e) {
      log.error("Can't set CommandLinetasks references", e);
      }
    String init = "";
    // Source init.bsh
    log.info("Sourcing init.bsh");
    try {
      init = new StringFile("init.bsh").toString();
      interpreter.eval(init);
      }
    catch (AstroLabNetException e) {
      log.warn("init.bsh file cannot be read.");
      log.debug("init.bsh file cannot be read.", e);
      }
    catch (EvalError e) {
      log.error("Can't evaluate standard BeanShell expression", e);
      }
    // Populate Servers
    if (servers().isEmpty()) {
      addServer("Local Host", "http://localhost:8998", "http://localhost:4040", "http://localhost:8080");
      }
    getServersFromTopology(servers());
    // Read Actions
    readActions();
    // Read Jobs
    readJobs();
    // Source command line source
    if (Init.source() != null) {
      log.info("Sourcing " + Init.source());
      try {
        init = new StringFile(Init.source()).toString();
        interpreter.eval(init);
        }
      catch (AstroLabNetException e) {
        log.warn(Init.source() + " file cannot be read.");
        log.debug(Init.source() + " file cannot be read.", e);
        }
      catch (EvalError e) {
        log.error("Can't evaluate standard BeanShell expression", e);
        }
      }
    }
    
  /** Get new {@link Server}s from topology table.
    * Runs recursively, stops whje n no new {@link Server} found.
    * @param servers The {@link List} of existing {@link Server}s to scan for new {@link Server}s. */
  private void getServersFromTopology(List<Server> servers) {
    List<Server> knownServers = new ArrayList<>();
    List<Server> newServers   = new ArrayList<>();
    for (Server s : servers) {
      knownServers.add(s);
      }
    for (Server server : knownServers) {
      log.info("Reading Topology Database " + server.urlHBase());
      try {
        String resultString = server.hbase().scan(Info.topology());
        JSONObject row;
        JSONArray cell;
        JSONObject column;
        Server server1;
        String name;
        String cname;
        String cvalue;
        String spark = null;
        String livy = null;
        String hbase = null;
        JSONArray result = new JSONObject(resultString).getJSONArray("Row");
        for (int i = 0; i < result.length(); i++) {
          row = result.getJSONObject(i);
          name = decode(row.getString("key"));
          cell = row.getJSONArray("Cell");
          spark = null;
          livy  = null;
          hbase = null;
          for (int j = 0; j < cell.length(); j++) {
            column = cell.getJSONObject(j);
            cname  = decode(column.getString("column"));
            cvalue = decode(column.getString("$"));
            switch (cname) {
              case "d:spark":
                spark = cvalue;
                break;
              case "d:livy":
                livy  = cvalue;
                break;
              case "d:hbase":
                hbase = cvalue;
                break;
              }
            }
          Server newServer = addServer(name, livy, spark, hbase);
          if (newServer != null) {
            newServers.add(newServer);
            }
          }
        }
      catch (Exception e) {
        log.warn("Cannot parse Topology table", e);
        }
      }
    if (!newServers.isEmpty()) {
      getServersFromTopology(newServers);
      }
    }    
    
  @Override
  public Server addServer(String name,
                          String urlLivy,
                          String urlSpark,
                          String urlHBase) {
    if (urlLivy == null) {
      log.warn("No Livy server defined for " + name);
      return null;
      }
    for (Server server : servers()) {
      if (name.equals(server.name())) {
        log.warn("Server " + name + " not updated");
        return null;
        }
      }
    Server server = new Server(name, urlLivy, urlSpark, urlHBase);
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
  public Job addJob(String   name,
                    String   file,
                    String   className) {
    file = FileSystems.getDefault().getPath(file).normalize().toAbsolutePath().toString();
    Job job = new Job(name, file, className);
    log.info("Adding Job: " + job);
    _jobs.add(job);
    return job;
    }
    
  @Override
  public Data addData(String name) {
    Data data = new Data(name);
    log.info("Adding Data: " + data);
    _datas.add(data);
    return data;
    }
        
  @Override
  public Channel addChannel(String name) {
    Channel channel = new Channel(name);
    log.info("Adding Channel: " + channel);
    _channels.add(channel);
    return channel;
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
  public Batch addBatch(String  name,
                        Server  server,
                        int     id) {
    Batch batch = new Batch(name, id, server);
    log.info("Adding Batch: " + batch);
    _batchs.add(batch);
    return batch;
    }
    
  @Override
  public Search addSearch(String name,
                          Source source) {
    Search search = new Search(name, source);
    log.info("Adding Search: " + search);
    _searches.add(search);
    return search;
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
  public List<Job> jobs() {
    return _jobs;
    }
    
  @Override
  public List<Data> datas() {
    return _datas;
    }
    
  @Override
  public List<Channel> channels() {
    return _channels;
    }
    
  @Override
  public List<Task> tasks() {
    return _tasks;
    }
    
  @Override
  public List<Batch> batchs() {
    return _batchs;
    }
    
  @Override
  public List<Search> searches() {
    return _searches;
    }
    
  /** Interpret <em>BeanShell</em> script.
    * @param The <em>BeanShell</em> script to be interpreted. */
  public void interpret(String text) {
   try {
      _interpreter.eval(text);
      }
    catch (EvalError e) {
      log.error("Can't evaluate BeanShell expression:\n" + text, e);
      }
    }
    
  /** Decode REST server string.
    * @param s The encoded REST server string.
    * @return The decode  REST server string. */
  private String decode(String s) {
    return  new String(Base64.getDecoder().decode(s));
    }
    
  private Interpreter _interpreter;
   
  private List<Server>  _servers  = new ArrayList<>();
  private List<Action>  _actions  = new ArrayList<>();
  private List<Job>     _jobs     = new ArrayList<>();
  private List<Data>    _datas    = new ArrayList<>();
  private List<Channel> _channels = new ArrayList<>();
  private List<Task>    _tasks    = new ArrayList<>();
  private List<Batch>   _batchs   = new ArrayList<>();
  private List<Search>  _searches = new ArrayList<>();
  
  /** Logging . */
  private static Logger log = Logger.getLogger(DefaultInteracter.class);
  
  }
