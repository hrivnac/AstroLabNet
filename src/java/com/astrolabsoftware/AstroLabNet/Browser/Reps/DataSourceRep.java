package com.astrolabsoftware.AstroLabNet.Browser.Reps;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.DB.*;

// Log4J
import org.apache.log4j.Logger;

/** <code>DataSourceRep</code> is {@link BrowserWindow} representation of {@link DataSource}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class DataSourceRep extends ElementRep {
  
  /** Create new DataSourceRep.
    * @param dataSource The represented {@link DataSource}.
    * @param browser The {@link BrowserWindow}. */
  public DataSourceRep(DataSource    dataSource,
                       BrowserWindow browser) {
    super(dataSource, browser);
    }
    
  /** Give the referenced {@link DataSource}.
    * @return The referenced {@link DataSource}. */
  public DataSource dataSource() {
    return (DataSource)element();
    }
    
  @Override
  public String toString() {
    return dataSource().toString();
    }
   
  /** Logging . */
  private static Logger log = Logger.getLogger(DataSourceRep.class);

  }
