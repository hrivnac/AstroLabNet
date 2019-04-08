package com.astrolabsoftware.AstroLabNet.Journal;

import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.HBaser.HBaseClient;
import com.astrolabsoftware.AstroLabNet.Utils.IDFactory;

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
    * @param actor   The actor be recorded.
    * @param action  The action to be recorded.
    * @param rc      The result code. 0 means success.
    * @param time    The time spend by command execution, in s.
    * @param before  The id of preceding action. May be <tt>null</tt>.
    * @param after   The id of following action. May be <tt>null</tt>.
    * @param result  The command result to be recorded. May be <tt>null</tt>.      
    * @param comment The comment to be recorded. May be <tt>null</tt>. */
  public void record(String actor,
                     String action,
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
          String[] columns = new String[]{"i:actor", "i:action", "d:rc", "d:time", "d:before", "d:after", "d:result", "c:comment"};
          String[] values  = new String[]{   actor ,    action ,    rc ,    time ,    before ,    after ,    result ,    comment };
          _hbase.put("astrolabnet.journal.1", IDFactory.newID(), columns, values);
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