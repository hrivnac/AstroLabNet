package com.astrolabsoftware.AstroLabNet.DB;

import com.astrolabsoftware.AstroLabNet.Livyser.Language;

// Log4J
import org.apache.log4j.Logger;

/** <code>Action</code> represents an action to be executed on <em>Spark</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Action extends Element {  
  
  /** Create new Action.
    * @param name     The Action name.
    * @param cmd      The command.
    * @param language The {@link Language} od the command. */
  public Action(String        name,
                String        cmd,
                Language      language) {
    super(name);
    _cmd      = cmd;
    _language = language;
    }

  /** Give the associated command text.
    * @return The associated command text. */
  public String cmd() {
    return _cmd;
    }
    
  /** Give the Action {@link Language}.
    * @return The Action {@link Language}. */
  public Language language() {
    return _language;
    }
    
  /** Set as a new Action, so it will be stored on Exit. */
  public void setNew() {
    _new = true;
    }
    
  /** Whether is new, to be stored on Exit.
    * return Whether is new. */
  public boolean isNew() {
    return _new;
    }
    
  @Override
  public String toString() {
    return name() + " (" + _language + ")";
    }

  private String _cmd;
  
  private Language _language;
  
  private boolean _new = false;
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Action.class);

  }
