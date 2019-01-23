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
    PropertyConfigurator.configure(Init.class.getClassLoader().getResource("com/astrolabsoftware/AstroLabNet/Utils/log4j.properties"));
    fixLog4J();
    //log.info("Initialised, version: " + Info.release());
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
          
  private static String[] ERROR = {"org.apache.hadoop.hbase.HBaseConfiguration"
                                   };
    
  /** Logging . */
  private static Logger log = Logger.getLogger(Init.class);

  }
