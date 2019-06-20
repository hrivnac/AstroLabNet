package com.astrolabsoftware.AstroLabNet.WebService;

import com.astrolabsoftware.AstroLabNet.Core.DefaultInteracter;
import com.astrolabsoftware.AstroLabNet.DB.Server;
import com.astrolabsoftware.AstroLabNet.DB.Action;
import com.astrolabsoftware.AstroLabNet.DB.Job;

// JHTools
import com.JHTools.Utils.Coding;
import com.JHTools.Graph.Node;
import com.JHTools.Graph.Nodes;
import com.JHTools.Graph.Edge;
import com.JHTools.Graph.Edges;

// Bean Shell
import bsh.Interpreter;

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
    
  /** Setup. */
  public void setup() {
    Interpreter interpreter = new Interpreter();
    setupInterpreter(interpreter);
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
    Node top = new Node("/:AstroLabNet",
                      "AstroLabNet",
                      "AstroLabNet",
                      "AstroLabNet",
                      "AstroLabNet",
                      "",
                      " ",
                      "0");
    _nodes.add(top);
    for (Server server : servers()) {
      node = new Node("Server:" + server.name(),
                      "Server",
                      server.name(),
                      server.name(),
                      server.urlLivy() + " " + server.urlSpark() + " " + server.urlHBase(),
                      server.name(),
                      " ",
                      "0");
      _nodes.add(node);
      _edges.add(new Edge(top, 
                          node,
                          " ",
                          " ",
                          " ",
                          " ",
                          "to",
                          " ",
                          "0"));
      pythonSessionNode = new Node("Session:Python Session on " + server.name(),
                                   "Session",
                                   "Python Session on " + server.name(),
                                   "Python",
                                   "Python",
                                   server.name(),
                                   " ",
                                   "0");
      scalaSessionNode = new Node("Session:Scala Session on " + server.name(),
                                  "Session",
                                  "Scala Session on " + server.name(),
                                  "Scala",
                                  "Scala",
                                  server.name(),
                                  " ",
                                  "0");
      senderNode = new Node("Sender:" + server.name(),
                            "Sender",
                             "Job Sender on " + server.name(),
                             " ",
                             " ",
                             server.name(),
                             " ",
                             "0");
      sourceNode = new Node("Source:" + server.name(),
                            "Source",
                            "Data Source on " + server.name(),
                            " ",
                            " ",
                            server.name(),
                            " ",
                            "0");
      topologyNode = new Node("Topology:" + server.name(),
                              "Topology",
                              "Topology of " + server.name(),
                              " ",
                              " ",
                              server.name(),
                              " ",
                              "0");
      catalogNode = new Node("Catalog:" + server.name(),
                             "Catalog",
                             "Catalog of " + server.name(),
                             " ",
                             " ",
                             server.name(),
                             " ",
                             "0");
      journalNode = new Node("Journal:" + server.name(),
                             "Journal",
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
    Node aj = new Node("Group:Actions and Jobs",
                       "Group",
                       "Actions and Jobs",
                       "Actions and Jobs",
                       " ",
                       "ActionJob",
                       "hexagon",
                       "0");
    _nodes.add(aj);
    _edges.add(new Edge(top, 
                        aj,
                        " ",
                        " ",
                        " ",
                        " ",
                        "to",
                        " ",
                        "0"));
    for (Action action : actions()) {
      actionNode = new Node("Action:" + action.name(),
                            "Action",
                            action.name(),
                            action.name(),
                            action.toString(),
                            "ActionJob",
                            " ",
                            "0");
      actionNode.put("cmd",      Coding.encode(action.cmd()));
      actionNode.put("language", action.language().toString());
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
      jobNode = new Node("Job:" + job.name(),
                         "Job",
                         job.name(),
                         job.name(),
                         job.toString(),
                         "ActionJob",
                         " ",
                         "0");
      jobNode.put("file",           job.file());
      jobNode.put("className",      job.className());
      jobNode.put("args",           job.args());
      jobNode.put("driverMemory",   job.driverMemory());
      jobNode.put("driverCores"   , String.valueOf(job.driverCores()));
      jobNode.put("executorMemory", job.executorMemory());
      jobNode.put("executorCores",  String.valueOf(job.executorCores()));
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
      
  /** Give all {@link Nodes}.
    * @return The {@link Nodes}. */
  public Nodes nodes() {
     return _nodes;
    }
    
  /** Give all {@link Edges}.
    * @return The {@link Edges}. */
  public Edges edges() {
     return _edges;
    }
    
  private Nodes _nodes;
  
  private Edges _edges;
      
  /** Logging . */
  private static Logger log = Logger.getLogger(WSCommand.class);
  
  }