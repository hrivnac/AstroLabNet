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
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Core.Interacter;

// Bean Shell
import bsh.Interpreter;

// Java
import java.util.Set;

/** Common interface exposed to am interactive User.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public interface Interacter {
  
  /** Fill the pre-defined {@link Action}s. */
  public void readActions();
  
  /** Fill the pre-defined {@link Job}s. */
  public void readJobs();
  
  /** Load standard init files and setup standard environment.
    * @param interpreter The embedded {@link Interpreter}. */
  public void setupInterpreter(Interpreter interpreter);
  
  /** Give named {@link Server}.
    * @param name The name of the {@link Server}.
    * @return     The named {@link Server},
    *             null, if it doesn't exist' */
   public Server server(String name);

   /** Give named {@link Action}.
    * @param name The name of the {@link Action}.
    * @return     The named {@link Action},
    *             null, if it doesn't exist' */
  public Action action(String name);
  
  // TBD: accessors for other elements

  /** Give named {@link Job}.
    * @param name The name of the {@link Job}.
    * @return     The named {@link Job},
    *             null, if it doesn't exist' */
  public Job job(String name);
 
  
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
  
  /** Add {@link Job}.
    * @param jobName        The {@link Job} name
    * @param file           The jar or py filename.
    * @param className      The <em>main</em> className or <tt>null</tt> for py file.
    * @param args           The Job args, if any. 
    * @param driverMemory   The Job driver memory or <tt>null</tt>. 
    * @param driverCores    The Job driver cores or <tt>0</tt>.  
    * @param executorMemory The Job executor memory or <tt>null</tt>. 
    * @param executorCores  The Job executor cores or <tt>0</tt>. 
    * @param numExecutors   The Job executots or <tt>0</tt>.
    * @param jars           The Job jars or <tt>null</tt>. 
    * @param pyFiles        The Job pyFiles or <tt>null</tt>. 
    * @param files          The Job files or <tt>null</tt>. 
    * @param archives       The Job archives or <tt>null</tt>. 
    * @param queue          The Job queue or <tt>null</tt>.
    * @param conf           The Job conf or <tt>null</tt>. 
    * @param proxyUser      The Job proxyUser or <tt>null</tt>.
    * @return               The added {@link Job}. */
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
                    String proxyUser);
  
  /** Add {@link Data}.
    * @param name The {@link Data} name
    * @return     The added {@link Data}. */
  public Data addData(String name);
    
  /** Add {@link Channel}.
    * @param name The {@link Channel} name.
    * @return     The added {@link Channel}. */
  public Channel addChannel(String name);
  
  /** Add {@link Task}.
    * @param name    The {@link Task} name.
    * @param session The hosting {@link Session}.
    * @param id      The Statement id.
    * @return        The added {@link Task}. */
  public Task addTask(String  name,
                      Session session,
                      int     id);
  
  /** Add {@link Batch}.
    * @param name    The {@link Batch} name.
    * @param sender  The hosting {@link Sender}.
    * @param id      The Batch id.
    * @return        The added {@link Batch}. */
  public Batch addBatch(String  name,
                        Sender  sender,
                        int     id);
  
  /** Add {@link Search}.
    * @param name   The {@link Search} name.
    * @param source The hosting {@link Source}.
    * @return       The added {@link Search}. */
  public Search addSearch(String  name,
                          Source source);
  
  /** Give {@link Set} of available {@link Server}s.
    * @return The {@link Set} of available {@link Server}s. */
  public Set<Server> servers();
  
  /** Give {@link Set} of available {@link Action}s.
    * @return The {@link Set} of available {@link Action}s. */
  public Set<Action> actions();
  
  /** Give {@link Set} of available {@link Job}s.
    * @return The {@link Set} of available {@link Job}s. */
  public Set<Job> jobs();
  
  /** Give {@link Set} of available {@link Data}s.
    * @return The {@link Set} of available {@link Data}s. */
  public Set<Data> datas();
   
  /** Give {@link Set} of available {@link Channel}s.
    * @return The {@link Set} of available {@link Channel}s. */
  public Set<Channel> channels();
   
  /** Give {@link Set} of available {@link Task}s.
    * @return The {@link Set} of available {@link Task}s. */
  public Set<Task> tasks();
   
  /** Give {@link Set} of available {@link Batch}s.
    * @return The {@link Set} of available {@link Batch}s. */
  public Set<Batch> batchs();
   
  /** Give {@link Set} of available {@link Search}s.
    * @return The {@link Set} of available {@link Search}s. */
  public Set<Search> searchs();

  }
