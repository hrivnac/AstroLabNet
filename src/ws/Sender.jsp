<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- AstroLabNet Sender-->
<!-- @author Julius.Hrivnac@cern.ch  -->

<jsp:useBean id="wsc" class="com.astrolabsoftware.AstroLabNet.WebService.WSCommand" scope="session" />

<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Init" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Info" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Journal.Record" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.DB.Job" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.DB.Server" %>

<%@ page import="com.JHTools.Utils.IDFactory" %>

<%@ page import="org.json.JSONObject" %>
<%@ page import="org.json.JSONArray" %>

<!--%@ page errorPage="ExceptionHandler.jsp" %-->

<head>
  </head>
  
<body bgcolor="#ddddee">
  <%  
    String jobName        = request.getParameter("jobName");
    String jarName        = request.getParameter("jarName");
    String className      = request.getParameter("className");
    String args           = request.getParameter("args");
    String driverMemory   = request.getParameter("driverMemory");
    String driverCoresS   = request.getParameter("driverCores");
    String executorMemory = request.getParameter("executorMemory");
    String executorCoresS = request.getParameter("executorCores");
    String numExecutorsS  = request.getParameter("numExecutors");
    String jars           = request.getParameter("jars");
    String pyFiles        = request.getParameter("pyFiles");
    String files          = request.getParameter("files");
    String archives       = request.getParameter("archives");
    String queue          = request.getParameter("queue");
    String name           = request.getParameter("name");
    String conf           = request.getParameter("conf");
    String proxyUser      = request.getParameter("proxyUser");
    String serverS        = request.getParameter("server");
    int driverCores   = 0;
    int executorCores = 0;
    int numExecutors  = 0;
    if (driverCoresS != null && !driverCoresS.trim().equals("")) {
      driverCores = new Integer(driverCoresS).intValue();
      }
    if (executorCoresS != null && !executorCoresS.trim().equals("")) {
      executorCores = new Integer(executorCoresS).intValue();
      }
    if (numExecutorsS != null && !numExecutorsS.trim().equals("")) {
      numExecutors = new Integer(numExecutorsS).intValue();
      }
    out.println("<u>Sending "              + jobName + " on " + serverS           + "</u><br/>");
    out.println("JAR/PY file: "            + jarName                              + "<br/>");
    out.println("class name: "             + className                            + "<br/>");
    out.println("args: "                   + args                                 + "<br/>");
    out.println("jars: "                   + jars                                 + "<br/>");
    out.println("pyFiles: "                + pyFiles                              + "<br/>");
    out.println("files: "                  + files                                + "<br/>");
    out.println("archives: "               + archives                             + "<br/>");
    out.println("queue: "                  + queue                                + "<br/>");
    out.println("conf: "                   + conf                                 + "<br/>");
    out.println("proxyUser: "              + proxyUser                            + "<br/>");
    out.println("driver cores/memory: "    + driverCores   + "/" + driverMemory   + "<br/>");
    out.println("executor cores/memory: "  + executorCores + "/" + executorMemory + "<br/>");
    out.println("executors: "              + numExecutors                         + "<br/>");
    out.println("<hr/>");
    out.flush();
    Server server = wsc.server(serverS);
    long time = System.currentTimeMillis();
    int id = server.livy().sendJob(jarName,
                                   className,
                                   args,
                                   driverMemory,
                                   driverCores,
                                   executorMemory,
                                   executorCores,
                                   numExecutors,
                                   jars,
                                   pyFiles,
                                   files,
                                   archives,
                                   queue,
                                   jobName,
                                   conf,
                                   proxyUser,
                                   Integer.MAX_VALUE,
                                   1);    
    String resultString;
    JSONObject result;
    String statex0 = null;
    String statex;
    while (true) {
      resultString = server.livy().checkBatchProgress(id, 10, 1);
      if (resultString != null) {
        result = new JSONObject(resultString);
        statex = result.getString("state");
        if (statex0 == null) {
          statex0 = statex;
          out.println("State: " + statex + "<br/>");
          out.flush();
          }
        if (!statex.equals(statex0)) {
          out.println("State: " + statex + "<br/>");
          out.flush();
          statex0 = statex;
          }
        if (statex.equals("success") || statex.equals("dead")) {
          break;
          }
        }
      Thread.sleep(1000); // 1s
      }
    out.println("<hr/>");
    JSONArray logArray = result.getJSONArray("log");    
    String fullLog = "";
    for (Object logEntry : logArray) {
      fullLog += logEntry.toString() + "\n";
      }
    resultString = server.livy().getBatchLog(id, 10, 1);
    result = new JSONObject(resultString);
    logArray = result.getJSONArray("log");
    fullLog += "\n\n";
    for (Object logEntry : logArray) {
      fullLog += logEntry.toString() + "\n";
      }
    time = (System.currentTimeMillis() - time) / 1000;
    String argss = "jobName = "        + jobName        + "\n"
                 + "jarName = "        + jarName        + "\n"
                 + "className = "      + className      + "\n"
                 + "args = "           + args           + "\n"
                 + "driverMemory = "   + driverMemory   + "\n"
                 + "driverCores = "    + driverCores    + "\n"
                 + "executorMemory = " + executorMemory + "\n"
                 + "executorCores = "  + executorCores  + "\n"
                 + "numExecutors = "   + numExecutors   + "\n"
                 + "jars = "           + jars           + "\n"
                 + "pyFiles = "        + pyFiles        + "\n"
                 + "files = "          + files          + "\n"
                 + "archives = "       + archives       + "\n"
                 + "queue = "          + queue          + "\n"
                 + "jobName = "        + jobName        + "\n"
                 + "conf = "           + conf           + "\n"
                 + "proxyUser = "      + proxyUser;
    new Record(server).record(IDFactory.newID(), "Job", "send", argss, 0, time, null, null, fullLog, "from WS"); // TBD: fill all fields
    out.println("<pre>" + fullLog + "</pre>");
    out.println("<hr/>" + time + "s spent");
    %>
  <script type="text/javascript">
    if ('<%=statex%>' == "success") {
      document.body.style.backgroundColor = "#ddddff";
      }
    else if ('<%=statex%>' == "dead") {
      document.body.style.backgroundColor = "#ffdddd";
      }
    </script>
  </body>
