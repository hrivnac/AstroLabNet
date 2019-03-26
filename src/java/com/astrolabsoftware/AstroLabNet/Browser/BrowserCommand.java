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
    log.info("Adding ServerRep: " + serverRep);
    _serverReps.getChildren().add(serverRep.item());
    _window.showServer(serverRep);
    return server;
    }
    
  @Override
  public Action addAction(String   name,
                          String   cmd,
                          Language language) {
    Action action = super.addAction(name, cmd, language);
    ActionRep actionRep = new ActionRep(action, _window);
    log.info("Adding ActionRep: " + actionRep);
    _actionReps.getChildren().add(actionRep.item());
    return action;
    }
    
  @Override
  public Data addData(String name) {
    Data data = super.addData(name);
    DataRep dataRep = new DataRep(data, _window);
    log.info("Adding DataRep: " + dataRep);
    _dataReps.getChildren().add(dataRep.item());
    return data;
    }

  @Override
  public Channel addChannel(String name) {
    Channel channel = super.addChannel(name);
    ChannelRep channelRep = new ChannelRep(channel, _window);
    log.info("Adding ChannelRep: " + channelRep);
    _channelReps.getChildren().add(channelRep.item());
    return channel;
    }

   @Override
   public Task addTask(String      name,
                       Session session,
                       int     id) {
    Task task = super.addTask(name, session, id);
    TaskRep taskRep = TaskRep.create(task, _window);
    log.info("Adding TaskRep: " + taskRep);
    _taskReps.getChildren().add(taskRep.item());
    return task;
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
    
  private BrowserWindow _window;  
    
  private TreeItem<ElementRep> _serverReps;
  private TreeItem<ElementRep> _actionReps;
  private TreeItem<ElementRep> _dataReps    = new TreeItem<>(new ElementRep(new Element("Data"),          _window));
  private TreeItem<ElementRep> _channelReps = new TreeItem<>(new ElementRep(new Element("Channels"), _window));
  private TreeItem<ElementRep> _taskReps    = new TreeItem<>(new ElementRep(new Element("Tasks"),         _window));
      
  /** Logging . */
  private static Logger log = Logger.getLogger(BrowserCommand.class);
  
  }
