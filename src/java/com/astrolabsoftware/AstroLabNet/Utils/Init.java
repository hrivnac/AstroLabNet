package com.astrolabsoftware.AstroLabNet.Utils;

// Log4J
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;

// Java
import java.util.Enumeration;

/** <code>Init></code> initialises <em>AstroLabNet</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Init {

  /** Initialise <em>AstroLabNet</em>. */
  public static void init() {
    init(false);
    }

  /** Initialise <em>AstroLabNet</em>.
    * @param minimal Whether to use minimal properties (without Browser). */
  public static void init(boolean minimal) {
    if (minimal) {
      PropertyConfigurator.configure(Init.class.getClassLoader().getResource(PROPERTIES_MINIMAL));
      }
    else {
      PropertyConfigurator.configure(Init.class.getClassLoader().getResource(PROPERTIES));
      }
    fixLog4J();
    }
    
  /** Modify the default Log4J setup for external packages. */
  private static void fixLog4J() {
    for (String s : WARN) {
      Logger.getLogger(s).setLevel(Level.WARN);
      }
    for (String s : ERROR) {
      Logger.getLogger(s).setLevel(Level.ERROR);
      }
    //Enumeration<Logger> e = LogManager.getCurrentLoggers();
    //while (e.hasMoreElements()) {
    //  e.nextElement().setLevel(Level.WARN); 
    //  }
    }
          
  private static String[] WARN = {"org.apache.zookeeper.ZooKeeper",
                                  "org.apache.zookeeper.ClientCnxn"};
          
  private static String[] ERROR = {"org.apache.hadoop.hbase.HBaseConfiguration"};
  
  private static String PROPERTIES         = "com/astrolabsoftware/AstroLabNet/Utils/log4j.properties";
  private static String PROPERTIES_MINIMAL = "com/astrolabsoftware/AstroLabNet/Utils/log4j-minimal.properties";
  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(Init.class);

  }
