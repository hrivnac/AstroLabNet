package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;

// Log4J
import org.apache.log4j.Logger;

/** <code>Channel</code> represents {@link Data} channel.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Channel extends Element {
  
  /** Create new Channel.
    * @param name    The Channel name.
    * @param browser The {@link BrowserWindow}. */
  public Channel(String        name,
                 BrowserWindow browser) {
    super(name, browser);
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Channel.class);

  }
