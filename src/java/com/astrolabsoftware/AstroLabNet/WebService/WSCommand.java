package com.astrolabsoftware.AstroLabNet.WebService;

import com.astrolabsoftware.AstroLabNet.Core.DefaultInteracter;
import com.astrolabsoftware.AstroLabNet.DB.Server;
import com.astrolabsoftware.AstroLabNet.DB.Action;
import com.astrolabsoftware.AstroLabNet.DB.Job;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;

// JHTools
import com.JHTools.Utils.Coding;
import com.JHTools.Graph.Node;
import com.JHTools.Graph.Nodes;
import com.JHTools.Graph.Edge;
import com.JHTools.Graph.Edges;

// Bean Shell
import bsh.Interpreter;

// Java
import java.util.Map;

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
    Node top = new Node("/:AstroLabNet",
                        "/",
                        "AstroLabNet",
                        " ",
                        " ",
                        " ",
                        "0",
                        null,
                        _nodes,
                        null);
    for (Server server : servers()) {
      node = new Node("Server:" + server.name(),
                      "Server",
                      server.name(),
                      server.name() + "<br/>" + server.urlLivy() + " " + server.urlSpark() + " " + server.urlHBase(),
                      server.name(),
                      " ",
                      "0",
                      top,
                      _nodes,
                      _edges);
      node.put("livy", server.urlLivy());
      node.put("spark", server.urlSpark());
      node.put("hbase", server.urlHBase());
      if (server.urlSparkHistory() != null) {
        node.put("sparkHistory", server.urlSparkHistory());
        }
      if (server.urlHadoop() != null) {
        node.put("hadoop", server.urlHadoop());
        }
      if (server.urlGanglia() != null) {
        node.put("ganglia", server.urlGanglia());
        }
      pythonSessionNode = new Node("Session:Python Session on " + server.name(),
                                   "Session",
                                   "Python Session on " + server.name(),
                                   "Python Session on " + server.name(),
                                   server.name(),
                                   " ",
                                   "0",
                                   node,
                                   _nodes,
                                   _edges);
      scalaSessionNode = new Node("Session:Scala Session on " + server.name(),
                                  "Session",
                                  "Scala Session on " + server.name(),
                                  "Scala Session on " + server.name(),
                                  server.name(),
                                  " ",
                                  "0",
                                  node,
                                  _nodes,
                                  _edges);
      senderNode = new Node("Sender:" + server.name(),
                            "Sender",
                             "Sender on " + server.name(),
                             "Sender on " + server.name(),
                             server.name(),
                             " ",
                             "0",
                             node,
                             _nodes,
                             _edges);
      sourceNode = new Node("Source:" + server.name(),
                            "Source",
                            "Source on " + server.name(),
                            "Source on " + server.name(),
                            server.name(),
                            " ",
                            "0",
                            node,
                            _nodes,
                            _edges);
      new Node("Topology:" + server.name(),
               "Topology",
               "Topology of " + server.name(),
               "Topology of " + server.name() + " at " + server.hbase().url(),
               server.name(),
               " ",
               "0",
               node,
               _nodes,
               _edges);
      new Node("Catalog:" + server.name(),
               "Catalog",
               "Catalog of " + server.name(),
               "Catalog of " + server.name() + " at " + server.hbase().url(),
               server.name(),
               " ",
               "0",
               node,
               _nodes,
               _edges);
      new Node("Journal:" + server.name(),
               "Journal",
               "Journal of " + server.name(),
               "Journal of " + server.name() + " at " + server.hbase().url(),
               server.name(),
               " ",
               "0",
               node,
               _nodes,
               _edges);
      for (Map.Entry<Integer, Language> p : server.livy().getSessions()) {
        new Node("Task:" + p.getKey() + " " + p.getValue(),
                 "Task",
                 "Task on " + server.name(),
                 "Task " + p.getKey() + " " + p.getValue() + " on " + server.name(),
                 server.name(),
                 " ",
                 "0",
                 p.getValue() == Language.SCALA ? scalaSessionNode : pythonSessionNode,
                 _nodes,
                 _edges);
        }
      for (int idBatch : server.livy().getBatches()) {
        new Node("Batch:" + new Integer(idBatch).toString(),
                 "Batch",
                 "Batch on " + server.name(),
                 "Batch " + new Integer(idBatch).toString() + " on " + server.name(),
                 server.name(),
                 " ",
                 "0",
                 senderNode,
                 _nodes,
                 _edges);
        }
      }
    Node actionNode;
    Node jobNode;
    Node aj = new Node("Group:Actions and Jobs",
                       "Group",
                       "Actions and Jobs",
                       "Actions and Jobs",
                       "ActionJob",
                       "hexagon",
                       "0",
                       top,
                       _nodes,
                       _edges);
    for (Action action : actions()) {
      actionNode = new Node("Action:" + action.name(),
                            "Action",
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
                          "to",
                          " ",
                          "0"));
      }
    for (Job job : jobs()) {
      jobNode = new Node("Job:" + job.name(),
                         "Job",
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
      jobNode.put("numExecutors",   String.valueOf(job.numExecutors()));
      jobNode.put("jars",           job.jars());
      jobNode.put("pyFiles",        job.pyFiles());
      jobNode.put("files",          job.files());
      jobNode.put("archives",       job.archives());
      jobNode.put("queue",          job.queue());
      jobNode.put("name",           job.name());
      jobNode.put("conf",           job.conf());
      jobNode.put("proxyUser",      job.proxyUser());
      _nodes.add(jobNode);
      _edges.add(new Edge(aj,
                          jobNode,
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