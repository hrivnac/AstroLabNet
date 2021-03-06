package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.DB.Job;
import com.astrolabsoftware.AstroLabNet.Browser.Components.Images;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers.JobEventHandler;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;

// JavaFX
import javafx.scene.control.MenuItem;

// Java
import java.util.List;

// Log4J
import org.apache.log4j.Logger;

/** <code>JobRep</code> is {@link BrowserWindow} representation of {@link Job}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class JobRep extends ElementRep {
  
  /** Create new JobRep.
    * @param job      The represented {@link Job}.
    * @param browser  The {@link BrowserWindow}. */
  public JobRep(Job           job,
                BrowserWindow browser) {
    super(job, browser, Images.JOB);
    }
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem execute = new MenuItem("Use for Batch",  Images.icon(Images.SESSION));
    execute.setOnAction(new JobEventHandler(this));
    menuItems.add(execute);
    return menuItems;
    }
    
  /** Activate {@link Session}. */
  public void activate() {
    SenderRep selected = browser().getSelectedSender();
    if (selected == null) {
      log.error("No Batch is selected");
      }
    else {
       browser().setSenderFile(file(),
                               className(),
                               args(),
                               driverMemory(),
                               driverCores(),
                               executorMemory(),
                               executorCores(),
                               numExecutors(),
                               jars(),
                               pyFiles(),
                               files(),
                               archives(),
                               queue(),
                               conf(),
                               proxyUser());
      }
    }

  /** Give the Job jar or py filename.
    * @return the Job jar or py filename. */
  public String file() {
    return job().file();
    }
    
  /** Give the Job <em>main</em> className or <tt>null</tt> for py file.
    * @return The <em>main</em> classNameor <tt>null</tt> for py file. */
  public String className() {
    return job().className();
    }
    
  /** Give the Job args.
    * @return The Job args, if any. */
   public String args() {
    return job().args();
    }
  /** Give the Job jars.
    * @return The Job jars, if any. */
  public String jars() {
    return job().jars();
    }
    
  /** Give the Job pyFiles.
    * @return The Job pyFiles, if any. */
  public String pyFiles() {
    return job().pyFiles();
    }
    
  /** Give the Job files.
    * @return The Job files, if any. */
  public String files() {
    return job().files();
    }
    
  /** Give the Job archives.
    * @return The Job archives, if any. */
  public String archives() {
    return job().archives();
    }
     
  /** Give the Job driver memory.
    * @return The Job driver memory, can be <tt>null</tt>. */
  public String driverMemory() {
    return job().driverMemory();
    }
    
  /** Give the Job driver cores.
    * @return The Job driver cores, can be <tt>0</tt>. */
  public int driverCores() {
    return job().driverCores();
    }
    
  /** Give the Job executor memory.
    * @return The Job executor memory, can be <tt>null</tt>. */
  public String executorMemory() {
    return job().executorMemory();
    }
    
  /** Give the Job executor cores.
    * @return The Job executor cores, can be <tt>0</tt>. */
  public int executorCores() {
    return job().executorCores();
    }
     
  /** Give the Job executors.
    * @return The Job executors, can be <tt>0</tt>. */
  public int numExecutors() {
    return job().numExecutors();
    }
   
  /** Give the Job queue.
    * @return The Job queue, if any. */
  public String queue() {
    return job().queue();
    }
    
  /** Give the Job conf.
    * @return The Job conf, if any. */
  public String conf() {
    return job().conf();
    }
    
  /** Give the Job proxyUser.
    * @return The Job proxyUser, if any. */
  public String proxyUser() {
    return job().proxyUser();
    }
   
  /** Give the referenced {@link Job}.
    * @return The referenced {@link Job}. */
  public Job job() {
    return (Job)element();
    }
    
  @Override
  public String toString() {
    return job().toString();
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(JobRep.class);

  }
