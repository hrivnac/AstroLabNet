package com.astrolabsoftware.AstroLabNet.Browser.Actions;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.control.TreeView;
import javafx.scene.control.MenuItem;

// Log4J
import org.apache.log4j.Logger;

/** <code>TreeCellEventHandler</code> handles {@link ContextMenuEvent}s
  * on tree cell.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class TreeCellEventHandler implements EventHandler<ContextMenuEvent> {

  /** Create.
    * @param treeView The associated {@link TreeView}.
    * @param treeCell The associated {@link TreeCell}.  */
  public TreeCellEventHandler(TreeView<Element> treeView,
                              TreeCellImpl      treeCell) {
    _treeView = treeView;
    _treeCell = treeCell;
    }
    
  /** Handle {@link ContextMenuEvent}. Create {@link MenuItem}s.
    * Handling is forwarded to appropriate {@link TreeCell#setMenuItems}. */
  @Override
  public void handle(ContextMenuEvent event) {
    Element element = _treeView.getFocusModel().getFocusedItem().getValue();
    _treeCell.setMenuItems(element.menuItems());
    }

  private TreeCellImpl _treeCell;
    
  private TreeView<Element> _treeView;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(TreeCellEventHandler.class);

  }
