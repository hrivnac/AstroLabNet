package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;

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
    * @param name     The ActionRep name.
    * @param browser  The {@link BrowserWindow}.
    * @param cmd      The command.
    * @param language The {@link Language} od the command. */
  public ActionRep(String        name,
                   BrowserWindow browser,
                   String        cmd,
                   Language      language) {
    super(name, browser, Images.ACTION);
    _cmd      = cmd;
    _language = language;
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
    else if (selected.language() != _language) {
      log.error("Action language " + _language + " != Session language " + selected.language());
      }
    else {
      browser().setSessionCmd(_cmd);
      }
    }

  /** Give the associated command text.
    * @return The associated command text. */
  public String cmd() {
    return _cmd;
    }
    
  /** Give the Action {@link Language}.
    * @return The Action {@link Language}. */
  public Language language() {
    return _language;
    }
    
  @Override
  public String toString() {
    return name() + " (" + _language + ")";
    }

  private String _cmd;
  
  private Language _language;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(ActionRep.class);

  }
