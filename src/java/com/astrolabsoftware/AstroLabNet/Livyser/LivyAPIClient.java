package com.astrolabsoftware.AstroLabNet.Livyser;

import com.astrolabsoftware.AstroLabNet.Utils.Init;
import com.astrolabsoftware.AstroLabNet.DB.Jobs.*;

// Livy
import org.apache.livy.LivyClientBuilder;
import org.apache.livy.LivyClient;

// Java
import java.net.URI;
import java.io.File;

// Log4J
import org.apache.log4j.Logger;

/** <code>LivyAPICLient</code> is the bridge to the <em>Livy</em> API service.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
// TBD: handle full answer, check for errors
public class LivyAPIClient {
  
  /** Selftest. */
  public static void main(String[] args) {
    Init.init(args, true);
    String livyUrl = "http://localhost:8998";
    String piJar = "/home/hrivnac/work/LSST/AstroLabNet/lib/PiJob.jar"; // TBD: without abs path
    int samples = 100;
    LivyClient client = null;
    try {
      client = new LivyClientBuilder().setURI(new URI(livyUrl)).build();
      System.err.printf("Uploading %s to the Spark context...\n", piJar);
      client.uploadJar(new File(piJar)).get();
      System.err.printf("Running PiJob with %d samples...\n", samples);
      double pi = client.submit(new PiJob(samples)).get();
      System.out.println("Pi is roughly: " + pi);
      }
    catch (Exception e) {
      log.fatal("Cannot run test !", e); // TBD: remove e
      log.debug("Cannot run test !", e);
      }
    finally {
      if (client != null) {
        client.stop(true);
        }
      } 
    }
  
  /** Connect to the server.
    * @param url The url of the server. */
  public LivyAPIClient(String url) {
    log.info("Connecting to Livy Server " + url);
    _url = url;
    }
    
  private String _url;

  /** Logging . */
  private static Logger log = Logger.getLogger(LivyAPIClient.class);

  }
