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
import javafx.scene.image.Image;

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
    ElementRep elementRep = new ElementRep(new Element("Actions"), _window);
    MenuItem create = new MenuItem("Create", Images.icon(Images.CREATE));
    create.setOnAction(new ActionsEventHandler());
    elementRep.addMenuItem(create);
    _actionReps = new TreeItem<>(elementRep);
    }
    
  @Override
  public Server addServer(String name,
                          String urlLivy,
                          String urlSpark) {
    Server server = super.addServer(name, urlLivy, urlSpark);
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
  public DataSource addDataSource(String name) {
    DataSource dataSource = super.addDataSource(name);
    DataSourceRep dataSourceRep = new DataSourceRep(dataSource, _window);
    log.info("Adding DataSourceRep: " + dataSourceRep);
    _dataSourceReps.getChildren().add(dataSourceRep.item());
    return dataSource;
    }

  @Override
  public DataChannel addDataChannel(String name) {
    DataChannel dataChannel = super.addDataChannel(name);
    DataChannelRep dataChannelRep = new DataChannelRep(dataChannel, _window);
    log.info("Adding DataChannelRep: " + dataChannelRep);
    _dataChannelReps.getChildren().add(dataChannelRep.item());
    return dataChannel;
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
    
  /** Give {@link TreeItem} of {@link DataSourceRep}.
    * @return The {@link TreeItem} of available {@link DataSourceRep}. */
  public TreeItem<ElementRep> dataSourceReps() {
    return _dataSourceReps;
    }
    
  /** Give {@link TreeItem} of {@link DataChannelRep}.
    * @return The {@link TreeItem} of available {@link DataChannelRep}. */
  public TreeItem<ElementRep> dataChannelReps() {
    return _dataChannelReps;
    }
    
  /** Give {@link TreeItem} of {@link TaskRep}.
    * @return The {@link TreeItem} of available {@link TaskRep}. */
  public TreeItem<ElementRep> taskReps() {
    return _taskReps;
    }
    
  private BrowserWindow _window;  
    
  private TreeItem<ElementRep> _serverReps      = new TreeItem<>(new ElementRep(new Element("Servers"),       _window));
  private TreeItem<ElementRep> _actionReps;
  private TreeItem<ElementRep> _dataReps        = new TreeItem<>(new ElementRep(new Element("Data"),          _window));
  private TreeItem<ElementRep> _dataSourceReps  = new TreeItem<>(new ElementRep(new Element("Data Sources"),  _window));
  private TreeItem<ElementRep> _dataChannelReps = new TreeItem<>(new ElementRep(new Element("Data Channels"), _window));
  private TreeItem<ElementRep> _taskReps        = new TreeItem<>(new ElementRep(new Element("Tasks"),         _window));
      
  /** Logging . */
  private static Logger log = Logger.getLogger(BrowserCommand.class);
  
  }
