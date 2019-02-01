package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;

// Log4J
import org.apache.log4j.Logger;

/** <code>Action</code> represents <em>Spark</em> job or transfer
  * ready to run.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Action extends Element {
  
  /** Create new Action.
    * @param name The Action name. */
  public Action(String   name,
                String   cmd,
                Language language) {
    super(name, Images.ACTION);
    _cmd      = cmd;
    _language = language;
    }

  @Override
  public String toString() {
    return name() + " (Language = " + _language + "): " + _cmd;
    }

  private String _cmd;
  
  private Language _language;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Action.class);

  }
