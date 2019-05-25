<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- AstroLabNet HBase Table-->
<!-- @author Julius.Hrivnac@cern.ch  -->

<jsp:useBean id="wsc" class="com.astrolabsoftware.AstroLabNet.WebService.WSCommand" scope="application" />

<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Init" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Info" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.DB.Server" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Livyser.Language" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.WebService.HBase2Table" %>

<%@ page import="org.json.JSONObject" %>

<!--%@ page errorPage="ExceptionHandler.jsp" %-->

<head>
  <link rel="stylesheet" type="text/css" href="sortable.css"/>
  <script type="text/javascript" src="sortable.js"></script>
  </head>
  
<body bgcolor="#ddddff">
  <%  
    String serverS = request.getParameter("server");
    String table   = request.getParameter("table");
    Server server = wsc.server(serverS);
    out.println("<h1><u>" + table + " table of " + serverS + " server</u></h1>");
    out.println("<h2>" + server + "</h2>");
    JSONObject json = server.hbase().scan2JSON("astrolabnet." + table + ".1", // TBD: should get from Info
                                               null,
                                               0,
                                               0,
                                               0);
    String html = new HBase2Table().htmlTable(json);
    out.println(html);
    %>
  </body>
