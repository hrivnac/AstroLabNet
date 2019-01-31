package com.astrolabsoftware.AstroLabNet.Browser.Components;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.DB.*;

// JavaFX
import javafx.scene.Node;
import javafx.scene.control.TreeView;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.TextFieldTreeCell;

/** <code>TreeCellImpl</code> implements {@link TextFieldTreeCell}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class TreeCellImpl extends TextFieldTreeCell<Element> {

  /** TBD */
  public TreeCellImpl(TreeView<Element> treeView) {
    super();
    ContextMenu menu = new ContextMenu();
    MenuItem help = new MenuItem("Help", Images.icon(Images.HELP));
    MenuItem use  = new MenuItem("Use");
    menu.getItems().addAll(help, use);
    setContextMenu(menu);
    menu.setOnAction(new TreeCellEventHandler(treeView));
    }
    
  }
