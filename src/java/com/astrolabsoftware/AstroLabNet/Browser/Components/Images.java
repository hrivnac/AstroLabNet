package com.astrolabsoftware.AstroLabNet.Browser.Components;

// JavaFX
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;

/** <code>Icons</code> contains standard {@link Icon}s.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class Images {
  
  /** Create {@link Node} from {@link Image}.
    * @param image The original {@link Image}.
    * @return      The result {@link Node}. */
  public static Node icon(Image image) {
    return new ImageView(image);
    }
  
  public static final ClassLoader myLoader = new Images().getClass().getClassLoader();

  public static final Image ASTROLAB = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/AstroLab.png"));
  public static final Image SPARK    = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/Spark.png"));
  public static final Image HBASE    = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/HBase.png"));
  public static final Image LIVY     = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/Livy.png"));
  public static final Image ACTION   = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/Action.png"));
  public static final Image SESSION  = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/Session.png"));
  public static final Image TASK     = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/Task.png"));
  public static final Image SOURCE   = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/Source.png"));
  public static final Image EXIT     = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/Exit.png"));
  public static final Image CREATE   = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/Create.png"));
  public static final Image SCRIPT   = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/Script.png"));
  public static final Image HELP     = new Image(myLoader.getResourceAsStream("com/astrolabsoftware/AstroLabNet/Browser/Components/images/Help.png"));

  }
    