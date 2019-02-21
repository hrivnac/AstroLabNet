package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.DB.*;

// Log4J
import org.apache.log4j.Logger;

/** <code>DataChannelRep</code> is {@link BrowserWindow} representation of {@link DataChannel}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class DataChannelRep extends ElementRep {
  
  /** Create new DataChannelRep.
    * @param dataChannel The represennted {@link DataChannel}.
    * @param browser The {@link BrowserWindow}. */
  public DataChannelRep(DataChannel   dataChannel,
                        BrowserWindow browser) {
    super(dataChannel, browser);
    }
    
  /** Give the referenced {@link DataChannel}.
    * @return The referenced {@link DataChannel}. */
  public DataChannel dataChannel() {
    return (DataChannel)element();
    }
    
  @Override
  public String toString() {
    return dataChannel().toString();
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(DataChannelRep.class);

  }
