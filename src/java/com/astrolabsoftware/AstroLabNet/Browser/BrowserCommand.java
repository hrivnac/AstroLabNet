package com.astrolabsoftware.AstroLabNet.Browser;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.CLI.CommandLine;

// JavaFX
import javafx.scene.control.TreeItem;
import javafx.scene.control.MenuItem;

// Log4J
import org.apache.log4j.Logger;

/** <code>BrowserCommand</code> is the root browser kernel for the
  * <em>AstroLabNet</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class BrowserCommand extends CommandLine {
    
  /** Create and coonect to {@link BrowserWindow}.
    * @param window The connected {@link BrowserWindow}. */
  public BrowserCommand(BrowserWindow window) { 
    _window = window;
    // Actions
    ElementRep actionsRep = new ElementRep(new Element("Actions"), _window);
    MenuItem createAction = new MenuItem("Create", Images.icon(Images.CREATE));
    createAction.setOnAction(new ActionsEventHandler(this));
    actionsRep.addMenuItem(createAction);
    _actionReps = new TreeItem<>(actionsRep);
    // Jobs
    ElementRep jobsRep = new ElementRep(new Element("Jobs"), _window);
    MenuItem createJob = new MenuItem("Create", Images.icon(Images.CREATE));
    createJob.setOnAction(new JobsEventHandler(this));
    jobsRep.addMenuItem(createJob);
    _jobReps = new TreeItem<>(jobsRep);
    // Servers
    ElementRep serversRep = new ElementRep(new Element("Servers"), _window);
    MenuItem createServer = new MenuItem("Create", Images.icon(Images.CREATE));
    createServer.setOnAction(new ServersEventHandler(this));
    serversRep.addMenuItem(createServer);
    _serverReps = new TreeItem<>(serversRep);
    }
    
  @Override
  public Server addServer(String name,
                          String urlLivy,
                          String urlSpark,
                          String urlHBase) {
    Server server = super.addServer(name, urlLivy, urlSpark, urlHBase);
    if (server == null) {
      return null;
      }
    ServerRep serverRep = ServerRep.create(server, _window);
    if (!_serverReps.getChildren().contains(serverRep.item())) { // TBD: refactor
      log.info("Adding ServerRep: " + serverRep);
      _serverReps.getChildren().add(serverRep.item());
      }
    _window.showServer(serverRep);
    return server;
    }
    
  @Override
  public Action addAction(String   name,
                          String   cmd,
                          Language language) {
    Action action = super.addAction(name, cmd, language);
    ActionRep actionRep = new ActionRep(action, _window);
    if (!_actionReps.getChildren().contains(actionRep.item())) {
      log.info("Adding ActionRep: " + actionRep);
      _actionReps.getChildren().add(actionRep.item());
      }
    return action;
    }
    
  @Override
  public Job addJob(String  name,
                    String  file,
                    String  className) {
    Job job = super.addJob(name, file, className);
    JobRep jobRep = new JobRep(job, _window);
    if (!_jobReps.getChildren().contains(jobRep.item())) {
      log.info("Adding JobRep: " + jobRep);
      _jobReps.getChildren().add(jobRep.item());
      }
    return job;
    }
    
  @Override
  public Data addData(String name) {
    Data data = super.addData(name);
    DataRep dataRep = new DataRep(data, _window);
    if (!_dataReps.getChildren().contains(dataRep.item())) {
      log.info("Adding DataRep: " + dataRep);
      _dataReps.getChildren().add(dataRep.item());
      }
    return data;
    }

  @Override
  public Channel addChannel(String name) {
    Channel channel = super.addChannel(name);
    ChannelRep channelRep = new ChannelRep(channel, _window);
    if (!_channelReps.getChildren().contains(channelRep.item())) {
      log.info("Adding ChannelRep: " + channelRep);
      _channelReps.getChildren().add(channelRep.item());
      }
    return channel;
    }

  @Override
  public Task addTask(String  name,
                      Session session,
                      int     id) {
    Task task = super.addTask(name, session, id);
    TaskRep taskRep = TaskRep.create(task, _window);
    if (!_taskReps.getChildren().contains(taskRep.item())) {
      log.info("Adding TaskRep: " + taskRep);
      _taskReps.getChildren().add(taskRep.item());
      }
    return task;
    }
    
  @Override
  public Batch addBatch(String      name,
                        Sender      sender,
                        int         id) {
    Batch batch = super.addBatch(name, sender, id);
    BatchRep batchRep = BatchRep.create(batch, _window);
    if (!_batchReps.getChildren().contains(batchRep.item())) {
      log.info("Adding BatchRep: " + batchRep);
      _batchReps.getChildren().add(batchRep.item());
      }
    sender.registerBatch(id);
    return batch;
    }
    
  @Override
  public Search addSearch(String name,
                          Source source) {
    Search search = super.addSearch(name, source);
    SearchRep searchRep = SearchRep.create(search, _window);
    if (!_searchReps.getChildren().contains(searchRep.item())) {
      log.info("Adding SearchRep: " + searchRep);
      _searchReps.getChildren().add(searchRep.item());
      }
    return search;
    }
    
  /** Give {@link TreeItem} of {@link ServerRep}.
    * @return The {@link TreeItem} of available {@link ServerRep}. */
  public TreeItem<ElementRep> serverReps() {
    return _serverReps;
    }
    
  /** Give {@link TreeItem} of {@link ActionRep}.
    * @return The {@link TreeItem} of available {@link ActionRep}. */
  public TreeItem<ElementRep> actionReps() {
    return _actionReps;
    }
    
  /** Give {@link TreeItem} of {@link JobRep}.
    * @return The {@link TreeItem} of available {@link JobRep}. */
  public TreeItem<ElementRep> jobReps() {
    return _jobReps;
    }
    
  /** Give {@link TreeItem} of {@link DataRep}.
    * @return The {@link TreeItem} of available {@link DataRep}. */
  public TreeItem<ElementRep> dataReps() {
    return _dataReps;
    }
    
  /** Give {@link TreeItem} of {@link ChannelRep}.
    * @return The {@link TreeItem} of available {@link ChannelRep}. */
  public TreeItem<ElementRep> channelReps() {
    return _channelReps;
    }
    
  /** Give {@link TreeItem} of {@link TaskRep}.
    * @return The {@link TreeItem} of available {@link TaskRep}. */
  public TreeItem<ElementRep> taskReps() {
    return _taskReps;
    }
    
  /** Give {@link TreeItem} of {@link BatchRep}.
    * @return The {@link TreeItem} of available {@link BatchRep}. */
  public TreeItem<ElementRep> batchReps() {
    return _batchReps;
    }

  /** Give {@link TreeItem} of {@link SearchRep}.
    * @return The {@link TreeItem} of available {@link SearchRep}. */
  public TreeItem<ElementRep> searchReps() {
    return _searchReps;
    }
    
  private BrowserWindow _window;  
    
  private TreeItem<ElementRep> _serverReps;
  private TreeItem<ElementRep> _actionReps;
  private TreeItem<ElementRep> _jobReps;
  private TreeItem<ElementRep> _dataReps    = new TreeItem<>(new ElementRep(new Element("Data"),     _window));
  private TreeItem<ElementRep> _channelReps = new TreeItem<>(new ElementRep(new Element("Channels"), _window));
  private TreeItem<ElementRep> _taskReps    = new TreeItem<>(new ElementRep(new Element("Tasks"),    _window));
  private TreeItem<ElementRep> _batchReps   = new TreeItem<>(new ElementRep(new Element("Batches"),  _window));
  private TreeItem<ElementRep> _searchReps  = new TreeItem<>(new ElementRep(new Element("Searches"), _window));
      
  /** Logging . */
  private static Logger log = Logger.getLogger(BrowserCommand.class);
  
  }
