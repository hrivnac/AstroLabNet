package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.DB.Element;
import com.astrolabsoftware.AstroLabNet.Browser.Components.Images;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;

// JavaFX
import javafx.scene.control.TreeItem;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;

// Java
import java.util.List;
import java.util.ArrayList;

// Log4J
import org.apache.log4j.Logger;

/** <code>ElementRep</code> is {@link BrowserWindow} representation of {@link Element}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ElementRep {

  /** Create new ElementRep.
    * @param element The represented {@link Element}.
    * @param browser The {@link BrowserWindow}. */
  public ElementRep(Element       element,
                    BrowserWindow browser) {
    _element = element;
    _browser = browser;
    _item    = new TreeItem<ElementRep>(this);
    }
     
  /** Create new ElementRep with an icon.
    * @param element The represented {@link Element}.
    * @param browser The {@link BrowserWindow}.
    * @param icon    The {@link Image} of icon. */
  public ElementRep(Element       element,
                    BrowserWindow browser,
                    Image         icon) {
    _element = element;
    _browser = browser;
    _item = new TreeItem<ElementRep>(this, Images.icon(icon));
    }
    
  /** Create appropriate {@link MenuItem}s.
    * Subclasses will add their own {@link MenuItem}s.
    * @return The created {@link MenuItem}s. */
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = new ArrayList<>();
    menuItems.add(new MenuItem("Help", Images.icon(Images.HELP)));
    menuItems.addAll(_menuItems);
    return menuItems;
    }
    
  /** Add a {@link MenuItem}.
    * Subclasses should <em>Override</em> <tt>menuItems(...)</tt> method instead.
    * @param menuItem The new {@link MenuItem}. */ 
  public void addMenuItem(MenuItem menuItem) {
    _menuItems.add(menuItem);
    }
    
  /** Give associated {@link TreeItem}.
    * @return The associated {@link TreeItem}. */
  public TreeItem<ElementRep> item() {
    return _item;
    }

    /** Give the Element name.
    * @return The Element name. */
  public String name() {
    return _element.name();
    }
    
  /** Give the {@link BrowserWindow}.
    * @return The {@link BrowserWindow}. */
  public BrowserWindow browser() {
    return _browser;
    }
    
  /** Give the referenced {@link Element}.
    * @return The referenced {@link Element}. */
  public Element element() {
    return _element;
    }
    
  @Override
  public String toString() {
    return element().name();
    }
          
  private Element _element;
  
  private BrowserWindow _browser;
  
  private TreeItem<ElementRep> _item;
     
  private List<MenuItem> _menuItems = new ArrayList<>();
 
  /** Logging . */
  private static Logger log = Logger.getLogger(ElementRep.class);

  }
