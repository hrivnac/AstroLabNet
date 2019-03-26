package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.DB.*;

// Java
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// Log4J
import org.apache.log4j.Logger;

/** <code>SourceRep</code> is {@link BrowserWindow} representation of {@link Source}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class SourceRep extends ElementRep {
  
  /** Create new SourceRep as a <em>Singleton</em>.
    * @param source The original {@link Source}.
    * @param browser The {@link BrowserWindow}. */
  public static SourceRep create(Source        source,
                                 BrowserWindow browser) {
    SourceRep sourceRep = _sourceReps.get(source.toString());
    if (sourceRep == null) {
      sourceRep = new SourceRep(source, browser);
      log.info("Adding Source " + sourceRep);
      _sourceReps.put(source.toString(), sourceRep);
      }
    return sourceRep;
    }
  
  private static Map<String, SourceRep> _sourceReps = new HashMap<>();  
  
  /** Create new SourceRep.
    * @param source  The represented {@link Source}.
    * @param browser The {@link BrowserWindow}. */
  public SourceRep(Source        source,
                   BrowserWindow browser) {
    super(source, browser, Images.SOURCE);
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
