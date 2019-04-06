package com.astrolabsoftware.AstroLabNet.Livyser;

import com.astrolabsoftware.AstroLabNet.DB.*;

// Log4J
import org.apache.log4j.Logger;

/** <code>LivyCLient</code> is the bridge to the <em>Livy</em> REST service
  * talking directly to <em>DB</em> {@link Element}s.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class LivyClient extends LivyRESTClient {
  
  /** Connect to the server.
    * @param url The url of the server. */
  public LivyClient(String url) {
    super(url);
    }
  /** Execute action, try until succeeds, wait for result.
    * @param Action The {@link Action} to be executed.
    * @return       The result as <em>Json</em> string. */
  public String executeAction(Action action) {
    return executeAction(action.cmd(), action.language());
    }
    
  /** Send job, try until succeeds, wait for result.
    * @param job The {@link Job} to be send.
    * @return    The result as <em>Json</em> string. */
  public String sendJob(Job job) {
    return sendJob(job.file(), job.className());
    }
    
  @Override
  public String toString() {
    return "LivyClient of " + super.toString();
    }
    
  private String _url;

  /** Logging . */
  private static Logger log = Logger.getLogger(LivyClient.class);

  }
