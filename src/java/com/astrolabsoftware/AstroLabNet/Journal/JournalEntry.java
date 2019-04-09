package com.astrolabsoftware.AstroLabNet.Journal;


/** The <em>JournalEntry</em> represents one <em>Journal</em> entry.
  * To be used in <em>JavaFX</em> {@link TableView}.
  * (It is a <em>JavaBean</em>.) 
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
// TBD: use proper (nonString) types in constructor
// TBD: add timestamp
public class JournalEntry {
  
  public JournalEntry() {}
  
  public JournalEntry(String key,
                      String actor,
                      String action,
                      String rc,
                      String time,
                      String before,
                      String after,
                      String result,
                      String comment) {
    _key     = key;
    _actor   = actor;
    _action  = action;
    _rc      = rc;
    _time    = time;
    _before  = before;
    _after   = after;
    _result  = result;
    _comment = comment;
    }
    
  public void setKey(String key) {
    _key = key;
    }
  
  public void setActor(String actor) {
    _actor = actor;
    }
  
  public void setAction(String action) {
    _action = action;
    }
  
  public void setRC(String rc) {
    _rc = rc;
    }
  
  public void setTime(String time) {
    _time = time;
    }
  
  public void setBefore(String before) {
    _before = before;
    }
  
  public void setAfter(String after) {
    _after = after;
    }
  
  public void setResult(String result) {
    _result = result;
    }
  
  public void setComment(String comment) {
    _comment = comment;
    }
    
  public String getKey() {
    return _key;
    }
  
  public String getActor() {
    return _actor;
    }
  
  public String getAction() {
    return _action;
    }
  
  public String getRC() {
    return _rc;
    }
  
  public String getTime() {
    return _time;
    }
  
  public String getBefore() {
    return _before;
    }
  
  public String getAfter() {
    return _after;
    }
  
  public String getResult() {
    return _result;
    }
  
  public String getComment() {
    return _comment;
    }
  
  private String _key     = null;
  private String _actor   = null;
  private String _action  = null;
  private String _rc      = null;
  private String _time    = null;
  private String _before  = null;
  private String _after   = null;
  private String _result  = null;
  private String _comment = null;
 
 }
