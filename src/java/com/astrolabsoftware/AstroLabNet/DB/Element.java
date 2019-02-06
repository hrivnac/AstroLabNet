package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;

// JavaFX
import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

// Java
import java.util.List;
import java.util.ArrayList;

// Log4J
import org.apache.log4j.Logger;

/** <code>Element></code> is generic database object.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Element {
  
  /** Create new Element.
    * @param name    The Element name.
    * @param browser The {@link BrowserWindow}. */
  public Element(String        name,
                 BrowserWindow browser) {
    _name    = name;
    _browser = browser;
    _item    = new TreeItem<Element>(this);
    }
     
  /** Create new Element with an icon.
    * @param name    The Element name.
    * @param browser The {@link BrowserWindow}.
    * @param icon    The {@link Image} of icon. */
  public Element(String        name,
                 BrowserWindow browser,
                 Image         icon) {
    _name = name;
    _browser = browser;
    _item = new TreeItem<Element>(this, Images.icon(icon));
    }
    
  /** Give the Element name.
    * @return The Element name. */
  public String name() {
    return _name;
    }
    
  /** Give the {@link BrowserWindow}.
    * @return The {@link BrowserWindow}. */
  public BrowserWindow browser() {
    return _browser;
    }
  
  /** Create appropriate {@link MenuItem}s.
    * Subclasses will add their own {@link MenuItem}s.
    * @return The created {@link MenuItem}s. */
  public List<MenuItem> menuItems() {
    List<MenuItem> menuItems = new ArrayList<>();
    menuItems.add(new MenuItem("Help", Images.icon(Images.HELP)));
    return menuItems;
    }
    
  /** Give associated {@link TreeItem}.
    * @return The associated {@link TreeItem}. */
  public TreeItem<Element> item() {
    return _item;
    }
    
  @Override
  public String toString() {
    return _name;
    }
          
  private String _name;
  
  private BrowserWindow _browser;
  
  private TreeItem<Element> _item;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Element.class);

  }
