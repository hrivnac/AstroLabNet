package com.astrolabsoftware.AstroLabNet.Browser.Actions;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.MenuItem;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventHandler;

// Log4J
import org.apache.log4j.Logger;

/** <code>TreeCellEventHandler</code> handles {@link Event}s
  * on {@link TreeCell}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class TreeCellEventHandler implements EventHandler {

  /** TBD */
  public TreeCellEventHandler(TreeView<Element> treeView) {
    _treeView = treeView;
    }
    
  /** TBD */
  @Override
  public void handle(Event event) {
    Element element = _treeView.getFocusModel().getFocusedItem().getValue();
    String action = null;
    EventTarget target = event.getTarget();
    if (target instanceof MenuItem) {
      action = ((MenuItem)target).getText();
      }
    log.info("Executing " + action + "  on " + element);
    }

  private TreeView<Element> _treeView;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(TreeCellEventHandler.class);

  }
