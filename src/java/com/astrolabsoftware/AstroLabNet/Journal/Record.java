package com.astrolabsoftware.AstroLabNet.Journal;

import com.astrolabsoftware.AstroLabNet.DB.Server;

// JHTools
import com.JHTools.HBaser.HBaseClient;

// Log4J
import org.apache.log4j.Logger;

/** The <em>Record</em> records <em>Journal</em> entry.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
// TBD: rename to Recorder
public class Record {
  
  /** Create Record manager.
    * @param server The {@link Server} to record to. */
  public Record(Server server) {
    _hbase = server.hbase();
    }
    
  /** Record entry.
    * Runs assynchronisely.
    * @param key     The record key, shoul be unique.
    * @param actor   The actor be recorded.
    * @param action  The action to be recorded.
    * @param args    The action arguments.
    * @param rc      The result code. 0 means success.
    * @param time    The time spend by command execution, in s.
    * @param before  The id of preceding action. May be <tt>null</tt>.
    * @param after   The id of following action. May be <tt>null</tt>.
    * @param result  The command result to be recorded. May be <tt>null</tt>.      
    * @param comment The comment to be recorded. May be <tt>null</tt>. */
  public void record(String key,
                     String actor,
                     String action,
                     String args,
                     int    rc,
                     long   time,
                     String before,
                     String after,
                     String result,
                     String comment) {
    record(key,
           actor,
           action,
           args,
           new Integer(rc).toString(),
           new Long(time).toString(),
           before,
           after,
           result,
           comment);
    }
  
  /** Record entry.
    * Runs assynchronisely.
    * @param key     The record key, shoul be unique.
    * @param actor   The actor be recorded.
    * @param action  The action to be recorded.
    * @param args    The action arguments.
    * @param rc      The result code. 0 means success.
    * @param time    The time spend by command execution, in s.
    * @param before  The id of preceding action. May be <tt>null</tt>.
    * @param after   The id of following action. May be <tt>null</tt>.
    * @param result  The command result to be recorded. May be <tt>null</tt>.      
    * @param comment The comment to be recorded. May be <tt>null</tt>. */
  public void record(String key,
                     String actor,
                     String action,
                     String args,
                     String rc,
                     String time,
                     String before,
                     String after,
                     String result,
                     String comment) {
    Thread thread = new Thread() {
      @Override
      public void run() {
        try {
          String[] columns = new String[]{"i:actor", "i:action", "i:args", "d:rc", "d:time", "d:before", "d:after", "d:result", "c:comment"};
          String[] values  = new String[]{   actor ,    action ,    args ,    rc ,    time ,    before ,    after ,    result ,    comment };
          _hbase.put("astrolabnet.journal.1", key, columns, values);
          }
        catch (Exception e) {
          log.debug("Can not record: " + action + " of " + actor , e);
          }
        }
      };
    thread.start();
    }
    
  private HBaseClient _hbase;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(Record.class);
  
  
  }