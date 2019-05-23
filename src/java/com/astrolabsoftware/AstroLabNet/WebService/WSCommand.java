package com.astrolabsoftware.AstroLabNet.WebService;

import com.astrolabsoftware.AstroLabNet.Core.DefaultInteracter;
import com.astrolabsoftware.AstroLabNet.DB.Server;
import com.astrolabsoftware.AstroLabNet.Graph.Node;
import com.astrolabsoftware.AstroLabNet.Graph.Nodes;

// Log4J
import org.apache.log4j.Logger;

/** <code>WSCommand</code> is the root Web Servic e kernel for the
  * <em>AstroLabNet</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class WSCommand extends DefaultInteracter {
    
  /** Create. */
  public WSCommand() { 
    // Populate Servers
    addServer("Local", "http://localhost:8998", "http://localhost:4040", "http://localhost:8080");
    getServersFromTopology(servers());
    // Read Actions
    //readActions();
    // Read Jobs
    //readJobs();
    }
    
    
  /** TBD */
  public Nodes servers2Nodes() {
    Nodes nodes = new Nodes();
    for (Server server : servers()) {
      nodes.add(new Node("Server",
                         server.name(),
                         server.name(),
                         server.toString(),
                         "",
                         "hexagon",
                         "0"));
      }
    return nodes;
    }
      
  /** Logging . */
  private static Logger log = Logger.getLogger(WSCommand.class);
  
  }
