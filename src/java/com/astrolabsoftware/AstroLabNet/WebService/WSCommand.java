package com.astrolabsoftware.AstroLabNet.WebService;

import com.astrolabsoftware.AstroLabNet.Core.DefaultInteracter;
import com.astrolabsoftware.AstroLabNet.DB.Server;
import com.astrolabsoftware.AstroLabNet.DB.Action;
import com.astrolabsoftware.AstroLabNet.DB.Job;
import com.astrolabsoftware.AstroLabNet.Graph.Node;
import com.astrolabsoftware.AstroLabNet.Graph.Nodes;
import com.astrolabsoftware.AstroLabNet.Graph.Edge;
import com.astrolabsoftware.AstroLabNet.Graph.Edges;
import com.astrolabsoftware.AstroLabNet.Utils.Coding;

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
    readActions();
    // Read Jobs
    readJobs();
    // Construct Graph  
    _nodes = new Nodes();
    _edges = new Edges();
    Node node;
    Node pythonSessionNode;
    Node scalaSessionNode;
    Node senderNode;
    Node sourceNode;
    Node topologyNode;
    Node catalogNode;
    Node journalNode;
    Node actionNode;
    Node jobNode;
    for (Server server : servers()) {
      node = new Node("Server",
                      server.name(),
                      server.name(),
                      server.urlLivy() + " " + server.urlSpark() + " " + server.urlHBase(),
                      server.name(),
                      " ",
                      "0");
      _nodes.add(node);
      pythonSessionNode = new Node("Session",
                                   "Python Session on " + server.name(),
                                   "Python",
                                   "Python",
                                   server.name(),
                                   " ",
                                   "0");
      scalaSessionNode = new Node("Session",
                                  "Scala Session on " + server.name(),
                                  "Scala",
                                  "Scala",
                                  server.name(),
                                  " ",
                                  "0");
      senderNode = new Node("Sender",
                             "Job Sender on " + server.name(),
                             " ",
                             " ",
                             server.name(),
                             " ",
                             "0");
      sourceNode = new Node("Source",
                            "Data Source on " + server.name(),
                            " ",
                            " ",
                            server.name(),
                            " ",
                            "0");
      topologyNode = new Node("Topology",
                              "Topology of " + server.name(),
                              " ",
                              " ",
                              server.name(),
                              " ",
                              "0");
      catalogNode = new Node("Catalog",
                             "Catalog of " + server.name(),
                             " ",
                             " ",
                             server.name(),
                             " ",
                             "0");
      journalNode = new Node("Journal",
                             "Journal of " + server.name(),
                             " ",
                             " ",
                             server.name(),
                             " ",
                             "0");
      _nodes.add(pythonSessionNode);
      _nodes.add(scalaSessionNode);
      _nodes.add(senderNode);
      _nodes.add(sourceNode);
      _nodes.add(topologyNode);
      _nodes.add(catalogNode);
      _nodes.add(journalNode);
      _edges.add(new Edge(node, 
                          pythonSessionNode,
                          " ",
                          " ",
                          " ",
                          " ",
                          "to",
                          " ",
                          "0"));
      _edges.add(new Edge(node,
                          scalaSessionNode,
                          " ",
                          " ",
                          " ",
                          " ",
                          "to",
                          " ",
                          "0"));
      _edges.add(new Edge(node,
                          senderNode,
                          " ",
                          " ",
                          " ",
                          " ",
                          "to",
                          " ",
                          "0"));
      _edges.add(new Edge(node,
                          sourceNode,
                          " ",
                          " ",
                          " ",
                          " ",
                          "to",
                          " ",
                          "0"));
      _edges.add(new Edge(node,
                          topologyNode,
                          " ",
                          " ",
                          " ",
                          " ",
                          "to",
                          " ",
                          "0"));
      _edges.add(new Edge(node,
                          catalogNode,
                          " ",
                          " ",
                          " ",
                          " ",
                          "to",
                          " ",
                          "0"));
      _edges.add(new Edge(node,
                          journalNode,
                          " ",
                          " ",
                          " ",
                          " ",
                          "to",
                          " ",
                          "0"));
      }
    Node aj = new Node("Group",
                       "Actions and Jobs",
                       "Actions and Jobs",
                       " ",
                       "ActionJob",
                       "hexagon",
                       "0");
    _nodes.add(aj);
    for (Action action : actions()) {
      actionNode = new Node("Action",
                            action.name() + " in " + action.language().toString(),
                            action.name(),
                            action.toString(),
                            "ActionJob",
                            " ",
                            "0");
      actionNode.put("cmd", Coding.encode(action.cmd()));
      _nodes.add(actionNode);
      _edges.add(new Edge(aj,
                          actionNode,
                          " ",
                          " ",
                          " ",
                          " ",
                          "to",
                          " ",
                          "0"));
     }
    for (Job job : jobs()) {
      jobNode = new Node("Job",
                         job.name(),
                         job.name(),
                         job.toString(),
                         "ActionJob",
                         " ",
                         "0");
      _nodes.add(jobNode);
      _edges.add(new Edge(aj,
                          jobNode,
                          " ",
                          " ",
                          " ",
                          " ",
                          "to",
                          " ",
                          "0"));
     }
    }
      
  /** TBD */
  public Nodes nodes() {
     return _nodes;
    }
    
  /** TBD */
  public Edges edges() {
     return _edges;
    }
    
  private Nodes _nodes;
  
  private Edges _edges;
      
  /** Logging . */
  private static Logger log = Logger.getLogger(WSCommand.class);
  
  }
