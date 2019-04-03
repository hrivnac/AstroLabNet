package com.astrolabsoftware.AstroLabNet.Browser.Actions;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.DB.Action;
import com.astrolabsoftware.AstroLabNet.DB.Job;

// JavaFX
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

// Java
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Base64;

// Log4J
import org.apache.log4j.Logger;

/** <code>ExitHandler</code> handles <em>Exit</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class ExitHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param w The connected {@link BrowserWindow}. */
  public ExitHandler(BrowserWindow w) {
    super();
    _w = w;
    }
    
  /** Close the {@link BrowserWindow}.
    * @param event The acted {@link ActionEvent}. */
  @Override
  public void handle(ActionEvent event) {
    log.info("Storing info in .state.bsh");
    try {
      PrintWriter writer = new PrintWriter(".state.bsh");
      for (Action action : _w.command().actions()) {
        if (action.isNew()) {
          writer.println("w.addAction(\"" + action.name() + "\", new String(Base64.getDecoder().decode(\"" + Base64.getEncoder().encodeToString(action.cmd().getBytes()) + "\")), Language." + action.language() + ").setNew();");
          }
        }
      for (Job job : _w.command().jobs()) {
        if (job.isNew()) {
          if (job.className() == null) {
            writer.println("w.addJob(\"" + job.name() + "\", \"" + job.file() + "\", null).setNew();");
            }
          else {
            writer.println("w.addJob(\"" + job.name() + "\", \"" + job.file() + "\", \"" + job.className() + "\").setNew();");
            }
          }
        }
      writer.close();
      }
    catch (FileNotFoundException e) {
      log.error("Cannot write .state.bsh");
      }
    _w.close();
    }
    
  private BrowserWindow _w;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(ExitHandler.class);

  }
