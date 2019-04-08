package com.astrolabsoftware.AstroLabNet.Core;

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
public class Record {
  
  /** Create Record manager.
    * @param server The {@link Server} to record to. */
  public Record(Server server) {
    _hbase = server.hbase();
    }
  
  /** Record entry.
    * @param actor   The actor be recorded.
    * @param action  The action to be recorded.
    * @param rc      The result code. 0 means success.
    * @param time    The time spend by command execution, in s.
    * @param before  The id of preceding action. May be <tt>null</tt>.
    * @param after   The id of following action. May be <tt>null</tt>.
    * @param comment The comment to be recorded. May be <tt>null</tt>.      
    * @param result  The command result to be recorded. May be <tt>null</tt>. */        
  public void record(String actor,
                     String action,
                     String rc,
                     String time,
                     String before,
                     String after,
                     String comment,
                     String result) {
    String[] columns = new String[]{"i:actor", "i:action", "d:rc", "d:time", "d:before", "d:after", "c:comment", "d:result"};
    String[] values  = new String[]{   actor ,    action ,    rc ,    time ,    before ,    after ,    comment ,    result };
    _hbase.put("astrolab.journal.1", IDFactory.newID(), columns, values);
    }
    
  private HBaseClient _hbase;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(Record.class);
  
  
  }