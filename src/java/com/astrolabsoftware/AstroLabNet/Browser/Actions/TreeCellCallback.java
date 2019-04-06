package com.astrolabsoftware.AstroLabNet.Browser.Actions;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;

// JavaFX
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/** <code>TreeCellCallback</code> handles {@link Callback}s
  * on {@link TreeCell}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class TreeCellCallback implements Callback<TreeView<ElementRep>, TreeCell<ElementRep>> {
  
  /** Return {@link TreeCell} corresponding to {@link TreeView}.
    * @param tv The {@link TreeView}.
    * @return   The corresponding {@link TreeCell}. */
  @Override
  public TreeCell<ElementRep> call(TreeView<ElementRep> tv) {
    return new TreeCellImpl(tv);
    }
    
  }    
