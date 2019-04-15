package com.astrolabsoftware.AstroLabNet.HBaser;


/** The <em>TableEntry</em> is ann entry for Java FX table.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public interface TableEntry {

  /** Set column value.
    * @param name  The column name.
    * @param value The column value. */
  public void set(String name, String value);
  
  /** Reset all coumn values. */
  public void reset();
  

  }
