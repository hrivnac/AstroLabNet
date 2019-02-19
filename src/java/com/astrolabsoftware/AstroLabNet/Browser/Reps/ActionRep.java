package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.scene.control.TreeItem;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

// Java
import java.util.List;

// Log4J
import org.apache.log4j.Logger;

/** <code>ActionRep</code> is {@link BrowserWindow} representation of {@link Action}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ActionRep extends ElementRep {
  
  /** Create new ActionRep.
    * @param action   The represented {@link Action}.
    * @param browser  The {@link BrowserWindow}. */
  public ActionRep(Action        action,
                   BrowserWindow browser) {
    super(action, browser, Images.ACTION);
    }
    
  @Override
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = super.menuItems();
    MenuItem execute = new MenuItem("Use for Session",  Images.icon(Images.SESSION));
    execute.setOnAction(new ActionEventHandler(this));
    menuItems.add(execute);
    return menuItems;
    }
    
  /** Activate {@link Session}. */
  public void activate() {
    SessionRep selected = browser().getSelectedSession();
    if (selected == null) {
      log.error("No Session is selected");
      }
    else if (selected.language() != action().language()) {
      log.error("Action language " + action().language() + " != Session language " + selected.language());
      }
    else {
      browser().setSessionCmd(action().cmd());
      }
    }

  /** Give the associated command text.
    * @return The associated command text. */
  public String cmd() {
    return action().cmd();
    }
    
  /** Give the Action {@link Language}.
    * @return The Action {@link Language}. */
  public Language language() {
    return action().language();
    }
    
  /** TBD */
  public Action action() {
    return (Action)element();
    }
    
  @Override
  public String toString() {
    return action().toString();
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(ActionRep.class);

  }
