package com.astrolabsoftware.AstroLabNet.Browser.Components;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.scene.control.TreeView;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.TextFieldTreeCell;

// Java
import java.util.List;

/** <code>TreeCellImpl</code> implements {@link TextFieldTreeCell}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class TreeCellImpl extends TextFieldTreeCell<Element> {

  /** Create. Add {@link ContextMenu}.
    * @param treeView The original {@link TreeView}. */
  public TreeCellImpl(TreeView<Element> treeView) {
    super();
    setContextMenu(_menu);
    setOnContextMenuRequested(new TreeCellEventHandler(treeView, this));
    _menu.setOnAction(new TreeCellContextMenuEventHandler(treeView));
    }
    
  public void setMenuItems(List<MenuItem> menuItems) {
    _menu.getItems().setAll(menuItems);
    }
    
  private ContextMenu _menu = new ContextMenu();
    
  }
