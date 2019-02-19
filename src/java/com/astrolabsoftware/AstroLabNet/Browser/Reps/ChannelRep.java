package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;

// Log4J
import org.apache.log4j.Logger;

/** <code>ChannelRep</code> is {@link BrowserWindow} representation of {@link Channel}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ChannelRep extends ElementRep {
  
  /** Create new ChannelRep.
    * @param name    The ChannelRep name.
    * @param browser The {@link BrowserWindow}. */
  public ChannelRep(String        name,
                    BrowserWindow browser) {
    super(name, browser);
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(ChannelRep.class);

  }
