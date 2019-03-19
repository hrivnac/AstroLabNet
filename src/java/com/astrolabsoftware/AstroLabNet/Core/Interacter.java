package com.astrolabsoftware.AstroLabNet.Core;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Core.Interacter;

// Bean Shell
import bsh.Interpreter;

// Java
import java.util.List;

/** Common interface exposed to am interactive User.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public interface Interacter {
  
  /** Fill the pre-defined {@link Action}s. */
  public void readActions();
  
  /** Load standard init files and setup standard environment.
    * @param interpreter The embedded {@link Interpreter}. */
  public void setupInterpreter(Interpreter interpreter);
  
  /** Add {@link Server}.
    * @param name     The {@link ServerRep} name.
    * @param urlLivy  The url of the <em>Livy</em> server. Should not be <tt>null</tt>.
    * @param urlSpark The url of the <em>Spark</em> server. May be <tt>null</tt>.
    * @param urlHBase The url of the <em>HBase</em> server (hosting <em>Catalog</em>. May be <tt>null</tt>.
    * @return         The added {@link Server}. */
  public Server addServer(String name,
                          String urlLivy,
                          String urlSpark,
                          String urlHBase);
  
  /** Add {@link Action}.
    * @param name     The {@link Action} name.
    * @param cmd      The command to execute.
    * @param language The {@link Language} of the command.
    * @return         The added {@link Action}. */
  public Action addAction(String   name,
                          String   cmd,
                          Language language);
  
  /** Add {@link Data}.
    * @param name The {@link Data} name
    * @return     The added {@link Data}. */
  public Data addData(String name);
  
  /** Add {@link DataSource}.
    * @param name The {@link DataSource} name.
    * @return     The added {@link DataSource}. */
  public DataSource addDataSource(String name);
  
  /** Add {@link DataChannel}.
    * @param name The {@link DataChannel} name.
    * @return     The added {@link DataChannel}. */
  public DataChannel addDataChannel(String name);
  
  /** Add {@link Task}.
    * @param name    The {@link Task} name.
    * @param session The hosting {@link Session}.
    * @param id      The statement id.
    * @return        The added {@link Task}. */
  public Task addTask(String  name,
                      Session session,
                      int     id);
  
  /** Give {@link List} of available {@link Server}s.
    * @return The {@link List} of available {@link Server}s. */
  public List<Server> servers();
  
  /** Give {@link List} of available {@link Action}s.
    * @return The {@link List} of available {@link Action}s. */
  public List<Action> actions();
  
  /** Give {@link List} of available {@link Data}s.
    * @return The {@link List} of available {@link Data}s. */
  public List<Data> datas();
   
  /** Give {@link List} of available {@link DataSource}s.
    * @return The {@link List} of available {@link DataSource}s. */
  public List<DataSource> dataSources();
   
  /** Give {@link List} of available {@link DataChannel}s.
    * @return The {@link List} of available {@link DataChannel}s. */
  public List<DataChannel> dataChannels();
   
  /** Give {@link List} of available {@link Task}s.
    * @return The {@link List} of available {@link Task}s. */
  public List<Task> tasks();

  }