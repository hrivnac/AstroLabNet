package com.astrolabsoftware.AstroLabNet.Utils;

// Java
import java.net.URL;
import java.net.HttpURLConnection;

// Log4J
import org.apache.log4j.Logger;

/** <code>Notifier</code> connects to Web page
  * to reqister call.
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Notifier {

  /** Connect to monitorring Web page.
    * Runs assynchronisely.
    * @param message The message to be send. */
  public static void notify(String message) {
    Thread thread = new Thread() {
      @Override
      public void run() {
        try {
          URL url = new URL("http://cern.ch/hrivnac/cgi-bin/record.pl?page=AstroLabNet_" + message + "_" + Info.release().replaceAll(" ", "_"));
          HttpURLConnection conn = (HttpURLConnection)url.openConnection();
          conn.setRequestMethod("GET");
          conn.getInputStream();
          }
        catch (Exception e) {
          log.debug("Can not notify: " + message, e);
          }
       }
      };
    thread.start();
    }
    
  /** Logging . */
  private static Logger log = Logger.getLogger(Notifier.class);
    
  }
