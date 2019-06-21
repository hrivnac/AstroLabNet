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
    String name           = request.getParameter("name");
    String jarName        = request.getParameter("jarName");
    String className      = request.getParameter("className");
    String args           = request.getParameter("args");
    String driverMemory   = request.getParameter("driverMemory");
    String driverCores    = request.getParameter("driverCores");
    String executorMemory = request.getParameter("executorMemory");
    String executorCores  = request.getParameter("executorCores");
    String serverS        = request.getParameter("server");
    Server server = wsc.server(serverS);
    out.println("<u>Sending "              + name + " on " + serverS + "</u><br/>");
    out.println("JAR/PY file: "            + jarName                          + "<br/>");
    out.println("class name: "             + className                        + "<br/>");
    out.println("args: "                   + args                             + "<br/>");
    out.println("driver cores/memory: "    + driverCores + "/" + driverMemory + "<br/>");
    out.println("executor cores/memory: "  + driverCores + "/" + driverMemory + "<br/>");
    out.println("<hr/>");
    out.flush();
    long time = System.currentTimeMillis();
    int id = server.livy().sendJob(jarName,
                                   className,
                                   args,
                                   driverMemory,
                                   new Integer(driverCores).intValue(),
                                   executorMemory,
                                   new Integer(executorCores).intValue(),
                                   Integer.MAX_VALUE,
                                   1);    
    String resultString;
    JSONObject result;
    String statex;
    while (true) {
      resultString = server.livy().checkBatchProgress(id, 10, 1);
      if (resultString != null) {
        result = new JSONObject(resultString);
        statex = result.getString("state");
        if (statex.equals("success") || statex.equals("dead")) {
          break;
          }
        }
      Thread.sleep(1000); // 1s
      }
    JSONArray logArray = result.getJSONArray("log");
    String fullLog = "";
    for (Object logEntry : logArray) {
      fullLog += logEntry.toString() + "\n";
      }
    String state = result.getString("state");
    resultString = server.livy().getBatchLog(id, 10, 1);
    result = new JSONObject(resultString);
    logArray = result.getJSONArray("log");
    fullLog += "\n\n";
    for (Object logEntry : logArray) {
      fullLog += logEntry.toString() + "\n";
      }
    time = (System.currentTimeMillis() - time) / 1000;
    new Record(server).record(IDFactory.newID(), "Job", "send", 0, time, null, null, fullLog, "testing"); // TBD: fill all fields
    out.println("<pre>" + fullLog + "</pre>");
    %>
  <script type="text/javascript">
    if ('<%=state%>' == "success") {
      document.body.style.backgroundColor = "#ddddff";
      }
    else if ('<%=state%>' == "dead") {
      document.body.style.backgroundColor = "#ffdddd";
      }
    </script>
  </body>
