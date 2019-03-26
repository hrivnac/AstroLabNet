package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.DB.*;

// Log4J
import org.apache.log4j.Logger;

/** <code>SourceRep</code> is {@link BrowserWindow} representation of {@link Source}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class SourceRep extends ElementRep {
  
  /** Create new DataSourceRep.
    * @param source  The represented {@link Source}.
    * @param browser The {@link BrowserWindow}. */
  public SourceRep(Source        source,
                   BrowserWindow browser) {
    super(source, browser);
    }
    
  /** Give the referenced {@link Source}.
    * @return The referenced {@link Source}. */
  public Source source() {
    return (Source)element();
    }
    
  @Override
  public String toString() {
    return source().toString();
    }
   
  /** Logging . */
  private static Logger log = Logger.getLogger(SourceRep.class);

  }
