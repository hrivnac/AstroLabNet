package com.astrolabsoftware.AstroLabNet.Core;

import com.astrolabsoftware.AstroLabNet.DB.Server;
import com.astrolabsoftware.AstroLabNet.DB.Action;
import com.astrolabsoftware.AstroLabNet.DB.Job;
import com.astrolabsoftware.AstroLabNet.DB.Data;
import com.astrolabsoftware.AstroLabNet.DB.Channel;
import com.astrolabsoftware.AstroLabNet.DB.Task;
import com.astrolabsoftware.AstroLabNet.DB.Batch;
import com.astrolabsoftware.AstroLabNet.DB.Search;
import com.astrolabsoftware.AstroLabNet.DB.Session;
import com.astrolabsoftware.AstroLabNet.DB.Source;
import com.astrolabsoftware.AstroLabNet.DB.Sender;
import com.astrolabsoftware.AstroLabNet.Utils.Init;
import com.astrolabsoftware.AstroLabNet.Utils.Info;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Core.Interacter;

// JHTools
import com.JHTools.Utils.StringFile;
import com.JHTools.Utils.StringResource;
import com.JHTools.Utils.Network;
import com.JHTools.Utils.CommonException;

// Bean Shell
import bsh.Interpreter;
import bsh.EvalError;

// org.json
import org.json.JSONObject;
import org.json.JSONArray;

// Java
import java.util.Set;
import java.util.HashSet;
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
    for (String actionTxt : new String[] {"PythonPiAction.py",
                                          "ScalaPiAction.scala"}) {
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
      catch (CommonException e) {
        log.error("Cannot load Action from " + actionTxt, e);
        }
      }
    }
    
  @Override
  public void readJobs() {
    addJob("JavaPiJob",   "local:/.../JavaPiJob.jar",  "com.astrolabsoftware.AstroLabNet.DB.Jobs.JavaPiJob",  null, null, 0, null, 0, 0, null, null, null, null, null, null, null); // TBD: should be automatic
    addJob("ScalaPiJob",  "local:/.../ScalaPiJob.jar", "com.astrolabsoftware.AstroLabNet.DB.Jobs.ScalaPiJob", null, null, 0, null, 0, 0, null, null, null, null, null, null, null); // TBD: should be automatic
    addJob("PythonPiJob", "local:/.../PythonPiJob.py",  null,                                                 null, null, 0, null, 0, 0, null, null, null, null, null, null, null); // TBD: should be automatic
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
      log.error("Can't set CommandLine references", e);
      }
    String init = "";
    // Source init.bsh
    log.info("Sourcing init.bsh");
    try {
      init = new StringFile("init.bsh").toString();
      interpreter.eval(init);
      }
    catch (CommonException e) {
      log.warn("init.bsh file cannot be read.");
      log.debug("init.bsh file cannot be read.", e);
      }
    catch (EvalError e) {
      log.error("Can't evaluate standard BeanShell expression", e);
      }
    // Load site profile
    log.info("Loading site profile: " + Init.profile());  
    try {
      init = new StringResource("com/astrolabsoftware/AstroLabNet/Core/" + Init.profile() + ".bsh").toString();
      interpreter.eval(init);
      }
    catch (CommonException e) {
      log.warn("Site profile " + Init.profile() + " cannot be loaded.");
      log.debug("Site profile " + Init.profile() + " cannot be loaded.", e);
      }
    catch (EvalError e) {
      log.error("Can't evaluate standard BeanShell expression", e);
      }
    // Populate Servers
    if (servers().isEmpty()) {
      log.info("Adding Default Local Server");  
      addServer("Local", "http://localhost:8998", "http://localhost:4040", "http://localhost:8080");
      }
    getServersFromTopology(servers());
    // Read Actions
    readActions();
    // Read Jobs
    readJobs();
    // Source .state.bsh
    log.info("Sourcing .state.bsh");
    try {
      init = new StringFile(".state.bsh").toString();
      interpreter.eval(init);
      }
    catch (CommonException e) {
      log.warn(".state.bsh file cannot be read.");
      log.debug(".state.bsh file cannot be read.", e);
      }
    catch (EvalError e) {
      log.error("Can't evaluate standard BeanShell expression", e);
      }
    // Source command line source
    if (Init.source() != null) {
      log.info("Sourcing " + Init.source());
      try {
        init = new StringFile(Init.source()).toString();
        interpreter.eval(init);
        }
      catch (CommonException e) {
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
    * @param servers The {@link Set} of existing {@link Server}s to scan for new {@link Server}s. */
  public void getServersFromTopology(Set<Server> servers) {
    log.info("Populating Servers");
    Set<Server> knownServers = new HashSet<>();
    Set<Server> newServers   = new HashSet<>();
    for (Server s : servers) {
      knownServers.add(s);
      }
    for (Server server : knownServers) {
      log.info("Reading Topology Database " + server.urlHBase());
      try {
        String resultString = server.hbase().scanEncoded(Info.topology(), null, 0, 0, 0);
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
        String hadoop = null;
        String sparkHistory = null;
        String ganglia = null;
        JSONArray result = new JSONObject(resultString).getJSONArray("Row");
        for (int i = 0; i < result.length(); i++) {
          row = result.getJSONObject(i);
          name = decode(row.getString("key"));
          cell = row.getJSONArray("Cell");
          spark = null;
          livy  = null;
          hbase = null;
          hadoop = null;
          sparkHistory = null;
          ganglia = null;
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
              case "d:hadoop":
                hadoop = cvalue;
                break;
              case "d:ganglia":
                ganglia = cvalue;
                break;
              case "d:sparkHistory":
                sparkHistory = cvalue;
                break;
              }
            }
          Server newServer = addServer(name, livy, spark, hbase, hadoop, ganglia, sparkHistory);
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
  public Server server(String name) {
    for (Server s : servers()) {
      if (s.name().equals(name)) {
        return s;
        }
      }
    return null;
    }
  
  @Override
  public Action action(String name) {
    for (Action a : actions()) {
      if (a.name().equals(name)) {
        return a;
        }
      }
    return null;
    }
    
  @Override
  public Job job(String name) {
    for (Job j : jobs()) {
      if (j.name().equals(name)) {
        return j;
        }
      }
    return null;
    }
    
  /** TBD */
  private Server addServer(String name,
                           String urlLivy,
                           String urlSpark,
                           String urlHBase,
                           String urlHadoop,
                           String urlGanglia,
                           String urlSparkHistory) {
    if (urlLivy == null) {
      log.warn("No Livy server defined for " + name);
      return null;
      }
    if (!Network.checkPort(urlLivy)) { 
      log.warn("Livy server " + name + " is unreachable: " + urlLivy);
      return null;
      }
    Server server = new Server(name, urlLivy, urlSpark, urlHBase);
    if (urlHadoop != null) {
      server.setUrlHadoop(urlHadoop);
      }
    if (urlGanglia != null) {
      server.setUrlGanglia(urlGanglia);
      }
    if (urlSparkHistory != null) {
      server.setUrlSparkHistory(urlSparkHistory);
      }
    if (_servers.contains(server)) {
      log.warn("Updating Server (but not depending servers): " + server);
      _servers.remove(server);
      _servers.add(server);
      return null;
      }
    else {
      log.info("Adding Server: " + server);
      _servers.add(server);
      return server;
      }
    }
    
  @Override
  public Server addServer(String name,
                          String urlLivy,
                          String urlSpark,
                          String urlHBase) {
    return addServer(name, urlLivy, urlSpark, urlHBase, null, null, null);
    }
    
  @Override
  public Action addAction(String   name,
                          String   cmd,
                          Language language) {
    Action action = new Action(name, cmd, language);
    if (!_actions.contains(action)) {
      log.info("Adding Action: " + action);
      _actions.add(action);
      }
    return action;
    }
          
  @Override
  public Job addJob(String jobName,
                    String file,
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
    Job job = new Job(jobName,
                      file,
                      className,
                      args,
                      driverMemory,
                      driverCores,
                      executorMemory,
                      executorCores,
                      numExecutors,
                      jars,
                      pyFiles,
                      files,
                      archives,
                      queue,
                      conf,
                      proxyUser);
    if (!_jobs.contains(job)) {
      log.info("Adding Job: " + job);
      _jobs.add(job);
      }
    return job;
    }
    
  @Override
  public Data addData(String name) {
    Data data = new Data(name);
    if (!_datas.contains(data)) {
      log.info("Adding Data: " + data);
      _datas.add(data);
      }
    return data;
    }
        
  @Override
  public Channel addChannel(String name) {
    Channel channel = new Channel(name);
    if (!_channels.contains(channel)) {
      log.info("Adding Channel: " + channel);
      _channels.add(channel);
      }
    return channel;
    }
    
  @Override
  public Task addTask(String  name,
                      Session session,
                      int     id) {
    Task task = new Task(name, session, id);
    if (!_tasks.contains(task)) {
      log.info("Adding Task: " + task);
      _tasks.add(task);
      }
    return task;
    }
    
  @Override
  public Batch addBatch(String  name,
                        Sender  sender,
                        int     id) {
    Batch batch = new Batch(name, sender, id);
    if (!_batchs.contains(batch)) {
      log.info("Adding Batch: " + batch);
      _batchs.add(batch);
      }
    return batch;
    }
    
  @Override
  public Search addSearch(String name,
                          Source source) {
    Search search = new Search(name, source);
    if (!_searchs.contains(search)) {
      log.info("Adding Search: " + search);
      _searchs.add(search);
      }
    return search;
    }

  @Override
  public Set<Server> servers() {
    return _servers;
    }
    
  @Override
  public Set<Action> actions() {
    return _actions;
    }

  @Override
  public Set<Job> jobs() {
    return _jobs;
    }
    
  @Override
  public Set<Data> datas() {
    return _datas;
    }
    
  @Override
  public Set<Channel> channels() {
    return _channels;
    }
    
  @Override
  public Set<Task> tasks() {
    return _tasks;
    }
    
  @Override
  public Set<Batch> batchs() {
    return _batchs;
    }
    
  @Override
  public Set<Search> searchs() {
    return _searchs;
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
    return new String(Base64.getDecoder().decode(s));
    }
    
  private Interpreter _interpreter;
   
  private Set<Server>  _servers  = new HashSet<>();
  private Set<Action>  _actions  = new HashSet<>();
  private Set<Job>     _jobs     = new HashSet<>();
  private Set<Data>    _datas    = new HashSet<>();
  private Set<Channel> _channels = new HashSet<>();
  private Set<Task>    _tasks    = new HashSet<>();
  private Set<Batch>   _batchs   = new HashSet<>();
  private Set<Search>  _searchs  = new HashSet<>();
  
  /** Logging . */
  private static Logger log = Logger.getLogger(DefaultInteracter.class);
  
  }
