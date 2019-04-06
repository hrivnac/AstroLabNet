package com.astrolabsoftware.AstroLabNet.Browser.Actions;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;

// JavaFX
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.MenuItem;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventHandler;

// Log4J
import org.apache.log4j.Logger;

/** <code>TreeCellContextMenuEventHandler</code> handles {@link Event}s
  * on {@link TreeCell} {@link ContextMenu}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class TreeCellContextMenuEventHandler implements EventHandler {

  /** Create.
    * @param treeView The associated {@link TreeView}. */
  public TreeCellContextMenuEventHandler(TreeView<ElementRep> treeView) {
    _treeView = treeView;
    }
    
  /** Handle global {@link Event}.
    * @param event The {@link Event} to be handled. */
  @Override
  public void handle(Event event) {
    ElementRep elementRep = _treeView.getFocusModel().getFocusedItem().getValue();
    String action = null;
    EventTarget target = event.getTarget();
    if (target instanceof MenuItem) {
      action = ((MenuItem)target).getText();
      }
    log.info("Executing " + action + " on " + elementRep);
    String elementName = elementRep.getClass().getSimpleName();
    switch (action) {
      case "Help":
        String helpText = "No Help is available";
        try {
          helpText = new StringResource("com/astrolabsoftware/AstroLabNet/DB/" + elementName.replaceAll("Rep", "") + "Help.txt").toString();
          }
        catch (AstroLabNetException e) {
          log.error("Cannot load help page for " + elementName);
          log.debug("Cannot load help page for " + elementName, e);
          }
        log.info("\n" + helpText);
        break;
      default: 
        log.debug("Not global action " + action);
        break;
       }
    }

  private TreeView<ElementRep> _treeView;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(TreeCellContextMenuEventHandler.class);

  }
