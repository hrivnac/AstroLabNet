package com.astrolabsoftware.AstroLabNet.Utils;

// Java
import java.net.URL;
import java.net.HttpURLConnection;

/** <code>Notifier</code> connects to Web page
  * to reqister call.
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Notifier {

  /** Connect to monitorring Web page.
    * @param message The message to be send. */
  public static void notify(String message) {
    try {
      URL url = new URL("http://cern.ch/hrivnac/cgi-bin/record.pl?page=AstroLabNet_" + message + "_" + Info.release().replaceAll(" ", "_"));
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("GET");
      conn.getInputStream();
      }
    catch (Exception e) {}
    }
    
  }
