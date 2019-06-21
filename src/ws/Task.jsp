<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- AstroLabNet Task -->
<!-- @author Julius.Hrivnac@cern.ch  -->

<jsp:useBean id="wsc" class="com.astrolabsoftware.AstroLabNet.WebService.WSCommand" scope="session" />

<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Init" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Info" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Journal.Record" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.DB.Action" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.DB.Server" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Livyser.Language" %>

<%@ page import="org.json.JSONObject" %>

<!--%@ page errorPage="ExceptionHandler.jsp" %-->

<head>
  </head>
  
<body bgcolor="#ddddee">
  <%  
    String id      = request.getParameter("id");
    String delete  = request.getParameter("delete");
    String serverS = request.getParameter("server");
    Server server = wsc.server(serverS);
    if (delete.equals("true")) {
      server.livy().deleteSession(new Integer(id));
      }
    else {
      out.println("<u>Task " + id + " on " + serverS + "</u><br/>");
      out.println("<hr/>");
      out.flush();
      String resultString;
      String resultFormed;
      for (int idStatement : server.livy().getStatements(new Integer(id))) {
        resultString = server.livy().waitForActionResult(new Integer(id), idStatement, 10, 10);
        resultFormed = new JSONObject(resultString).toString(2);
        out.println("<pre>" + resultFormed + "</pre><hr/>");
        out.flush();
        }
      }
    %>
  <script type="text/javascript">
    document.body.style.backgroundColor = "#ddddff";
    </script>
  </body>
