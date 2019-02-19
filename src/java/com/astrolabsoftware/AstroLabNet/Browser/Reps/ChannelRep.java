package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.DB.*;

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
    * @param channel The represennted {@link Channel}.
    * @param browser The {@link BrowserWindow}. */
  public ChannelRep(Channel       channel,
                    BrowserWindow browser) {
    super(channel, browser);
    }
    
  /** TBD */
  public Channel channel() {
    return (Channel)element();
    }
    
  @Override
  public String toString() {
    return channel().toString();
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(ChannelRep.class);

  }
