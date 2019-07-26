<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- AstroLabNet Session-->
<!-- @author Julius.Hrivnac@cern.ch  -->

<jsp:useBean id="wsc" class="com.astrolabsoftware.AstroLabNet.WebService.WSCommand" scope="session" />

<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Init" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Info" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Journal.Record" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.DB.Action" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.DB.Server" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Livyser.Language" %>

<%@ page import="com.JHTools.Utils.IDFactory" %>

<%@ page import="org.json.JSONObject" %>

<!--%@ page errorPage="ExceptionHandler.jsp" %-->

<head>
  </head>
  
<body bgcolor="#ddddee">
  <%  
    String name      = request.getParameter("name");
    String script    = request.getParameter("script");
    String languageS = request.getParameter("language");
    String serverS   = request.getParameter("server");
    script = script.replaceAll("\\n", "").replaceAll("\\r", "\n");
    Language language = null;
    switch (languageS) {
      case "Scala":
        language = Language.SCALA;
        break;
      case "Python":
        language = Language.PYTHON;
        break;
      default:
        language = Language.SCALA;
        out.println("<em>language " + languageS + " not known/supported - using SCALA</em><br/>");
        break;
      }
    Action action = wsc.addAction(name, script, language);
    Server server = wsc.server(serverS);
    out.println("<u>Executing " + name + " on " + serverS + "  in " + language + "</u><br/>");
    out.println("<pre>" + script + "</pre>");
    out.println("<hr/>");
    out.flush();
    long time = System.currentTimeMillis();
    String resultString = server.livy().executeAction(action);
    String resultFormed = new JSONObject(resultString).toString(2);
    time = (System.currentTimeMillis() - time) / 1000;
    String args = "name = " + name + "\n"
                + "language = " + language + "\n"
                + "script = \n" + script;
    new Record(server).record(IDFactory.newID(), "Action", "execute", args, 0, time, null, null, resultFormed, "from WS"); // TBD: fill all fields
    out.println("<pre>" + resultFormed + "</pre>");
    out.println("<hr/>" + time + "s spent");
    %>
  <script type="text/javascript">
    document.body.style.backgroundColor = "#ddddff";
    </script>
  </body>
