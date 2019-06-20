<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- AstroLabNet Session-->
<!-- @author Julius.Hrivnac@cern.ch  -->

<jsp:useBean id="wsc" class="com.astrolabsoftware.AstroLabNet.WebService.WSCommand" scope="session" />

<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Init" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Info" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.DB.Action" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.DB.Server" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Livyser.Language" %>

<%@ page import="org.json.JSONObject" %>

<!--%@ page errorPage="ExceptionHandler.jsp" %-->

<head>
  <script type="text/javascript" src="Cookies.js"></script>
  </head>
  
<body bgcolor="#ddddee">
  <%  
    String name      = request.getParameter("name");
    String script    = request.getParameter("script");
    String languageS = request.getParameter("language");
    String serverS   = request.getParameter("server");
    name = name.split(" ")[0]; // TBD: check language
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
        // TBD
        break;
      }
    Action action = wsc.addAction(name, script, language);
    Server server = wsc.server(serverS);
    out.println("<u>Executing " + name + " on " + serverS + "  in " + language + "</u><br/>");
    out.println("<pre>" + script + "</pre>");
    out.println("<hr/>");
    out.flush();
    String resultString = server.livy().executeAction(action);
    String resultFormed = new JSONObject(resultString).toString(2);
    out.println("<pre>" + resultFormed + "</pre>");
    %>
  <script type="text/javascript">
    document.body.style.backgroundColor = "#ddddff";
    </script>
  </body>
