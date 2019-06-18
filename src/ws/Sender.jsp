<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- AstroLabNet Sender-->
<!-- @author Julius.Hrivnac@cern.ch  -->

<jsp:useBean id="wsc" class="com.astrolabsoftware.AstroLabNet.WebService.WSCommand" scope="session" />

<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Init" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Info" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.DB.Job" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.DB.Server" %>

<%@ page import="org.json.JSONObject" %>

<!--%@ page errorPage="ExceptionHandler.jsp" %-->

<head>
  </head>
  
<body bgcolor="#ddddee">
  <%  
    String name           = request.getParameter("name");
    String jarName        = request.getParameter("place") + request.getParameter("jarName");
    String className      = request.getParameter("className");
    String args           = request.getParameter("args");
    String driverMemory   = request.getParameter("driverMemory");
    String driverCores    = request.getParameter("driverCores");
    String executorMemory = request.getParameter("executorMemory");
    String executorCores  = request.getParameter("executorCores");
    String serverS        = request.getParameter("server");
    name = name.split(" ")[0];
    Job job = wsc.addJob(name,
                         jarName,
                         className,
                         args,
                         driverMemory,
                         new Integer(driverCores).intValue(),
                         executorMemory,
                         new Integer(executorCores).intValue());
    Server server = wsc.server(serverS);
    jarName = jarName.replaceAll("SERVER", server.urlSpark());
    out.println("<u>Sending "              + name + " on " + serverS + "</u><br/>");
    out.println("JAR/PY file: "            + jarName                          + "<br/>");
    out.println("class name: "             + className                        + "<br/>");
    out.println("args: "                   + args                             + "<br/>");
    out.println("driver cores/memory: "    + driverCores + "/" + driverMemory + "<br/>");
    out.println("executor cores/memory: "  + driverCores + "/" + driverMemory + "<br/>");
    out.println("<hr/>");
    out.flush();
    String resultString = server.livy().sendJob(job);
    String resultFormed = new JSONObject(resultString).toString(2);
    out.println("<pre>" + resultFormed + "</pre>");
    %>
  <script type="text/javascript">
    document.body.style.backgroundColor = "#ddddff";
    </script>
  </body>
