package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.DB.Data;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;

// Log4J
import org.apache.log4j.Logger;

/** <code>DataRep</code>  is {@link BrowserWindow} representation of {@link Data}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class DataRep extends ElementRep {
  
  /** Create new DataRep.
    * @param data    The represented {@link Data}. 
    * @param browser The {@link BrowserWindow}. */
  public DataRep(Data          data, 
                 BrowserWindow browser) {
    super(data, browser);
    }
    
  /** Give the referenced {@link Data}.
    * @return The referenced {@link Data}. */
  public Data data() {
    return (Data)element();
    }
    
  @Override
  public String toString() {
    return data().toString();
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(DataRep.class);

  }
