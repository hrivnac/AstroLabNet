package com.astrolabsoftware.AstroLabNet.Utils;

// Log4J
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;

// CLI
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

// Java
import java.util.Enumeration;
import java.util.Arrays;

/** <code>Init></code> initialises <em>AstroLabNet</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
// TBD: not static
public class Init {

  /** Initialise <em>AstroLabNet</em>.
    * @param args The cli arguments. */
  public static void init(String[] args) {
    init(args, false);
    }

  /** Minimal initialisation of <em>AstroLabNet</em>. */
  public static void init() {
    PropertyConfigurator.configure(Init.class.getClassLoader().getResource(PROPERTIES_MINIMAL));
    fixLog4J();
    Notifier.notify("");
    }
    
  /** Initialise <em>AstroLabNet</em>.
    * @param args    The cli arguments.
    * @param minimal Whether to use minimal properties (without Browser). */
  public static void init(String[] args,
                          boolean  minimal) {
    parseArgs(args);
    if (quiet()) {
      PropertyConfigurator.configure(Init.class.getClassLoader().getResource(PROPERTIES_MINIMALISTIC));
      }
    else if (asCLI()) {
      PropertyConfigurator.configure(Init.class.getClassLoader().getResource(PROPERTIES_MINIMAL));
      }
    else {
      PropertyConfigurator.configure(Init.class.getClassLoader().getResource(PROPERTIES));
      }
    fixLog4J();
    Notifier.notify(Arrays.toString(args));
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
          
  /** Parse the cli arguments.
    * @param args The cli arguments. */
  // TBD: find profiles
  private static void parseArgs(String[] args) {
    CommandLineParser parser = new BasicParser();
    Options options = new Options();
    options.addOption("h", "help",    false, "show help");
    options.addOption("b", "browser", false, "start graphical browser (default)");
    options.addOption("c", "cli",     false, "start command line");
    options.addOption("q", "quiet",   false, "minimal direct feedback");
    options.addOption(OptionBuilder.withLongOpt("source")
                                   .withDescription("source bsh file (init.bsh is also read)")
                                   .hasArg()
                                   .withArgName("file")
                                   .create("s"));
    options.addOption(OptionBuilder.withLongOpt("profile")
                                   .withDescription("use existing site profile Local (default) or LAL")
                                   .hasArg()
                                   .withArgName("name")
                                   .create("p"));
    try {
      CommandLine line = parser.parse(options, args );
      if (line.hasOption("help")) {
        new HelpFormatter().printHelp("java -jar AstroLabNet.exe.jar", options);
        System.exit(0);
        }
      if (line.hasOption("cli")) {
        _asCLI = true;
        }
      if (line.hasOption("browser")) {
        _asBrowser = true;
        }
      if (line.hasOption("quiet")) {
        _quiet = true;
        }
      if (line.hasOption("source")) {
        _source = line.getOptionValue("source");
        }
      if (line.hasOption("profile")) {
        _profile = line.getOptionValue("profile");
        }
      }
    catch (ParseException e) {
      new HelpFormatter().printHelp("java -jar AstroLabNet.exe.jar", options);
      System.exit(-1);
      }
    if ((!_asCLI & !_asBrowser) | (_asCLI & _asBrowser)) {
      _asBrowser = true;
      } 
    }
    
  /** Whether the application should start as a CLI.
    * @return Whether the application should start as a CLI. */
  public static boolean asCLI() {
    return _asCLI;
    }
    
  /** Whether the application should start in a browser.
    * @return Whether the application should start in a browser. */
  public static boolean asBrowser() {
    return _asBrowser;
    }
  /** Whether comamnd feedback should be absolutely minimal.
    * @return Whether comamnd feedback should be absolutely minimal. */
  public static boolean quiet() {
    return _quiet;
    }
    
  /** The <em>BeanShell</em> script to be sourced.
    * @return The <em>BeanShell</em> script to be sourced. May be <tt>null</tt>. */
  public static String source() {
    return _source;
    }
    
  /** The site profile to be used.
    * @return The site profile name. The default is <tt>Local</tt>. */
  public static String profile() {
    return _profile;
    }
    
  private static boolean _asCLI = false;  
    
  private static boolean _asBrowser = false;  
  
  private static boolean _quiet = false;
  
  private static String _source = null;
  
  private static String _profile = "Local";
    
  private static String[] WARN = {"org.apache.zookeeper.ZooKeeper",
                                  "org.apache.zookeeper.ClientCnxn"};
          
  private static String[] ERROR = {"org.apache.hadoop.hbase.HBaseConfiguration"};
  
  private static String PROPERTIES              = "com/astrolabsoftware/AstroLabNet/Utils/log4j.properties";
  private static String PROPERTIES_MINIMAL      = "com/astrolabsoftware/AstroLabNet/Utils/log4j-minimal.properties";
  private static String PROPERTIES_MINIMALISTIC = "com/astrolabsoftware/AstroLabNet/Utils/log4j-minimalistic.properties";
  
    
  /** Logging . */
  private static Logger log = Logger.getLogger(Init.class);

  }
