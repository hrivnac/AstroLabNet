<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- AstroLabNet Command Center-->
<!-- @author Julius.Hrivnac@cern.ch  -->

<jsp:useBean id="wsc" class="com.astrolabsoftware.AstroLabNet.WebService.WSCommand" scope="application" />

<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Init" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Info" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.WebService.WSCommand" %>

<!--%@ page errorPage="ExceptionHandler.jsp" %-->

<head>
  <script type="text/javascript" src="vis-4.21.0/dist/vis.js"></script>
  <script type="text/javascript" src="Options.js"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
  <link href="vis-4.21.0/dist/vis-network.min.css" rel="stylesheet" type="text/css"/>  
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/start/jquery-ui.css" rel="stylesheet"  type="text/css"/>
  <link href="CommandCenter.css" rel="stylesheet"  type="text/css"/>
  </head>
  
<body>
  <table width="100%" height="30%" id="Table">
    <tr height="20%">
      <td bgcolor="#ddffdd" height="20%">
        <div id="commands" title="commands">
          <table>
            <tr>
              <td><img src="images/AstroLab.png" width="100"/></td>
              <td><h1>AstroLabNet</h1>
                  <h2>@VERSION@ [@BUILD@]</h2>
                  </td>
              <td><a href="https://hrivnac.web.cern.ch/hrivnac/Activities/Packages/AstroLabNet" target="RESULT">AstroLabNet Home</a><br/>
                  <a href="https://astrolabsoftware.github.io" target="RESULT">AstroLab Sotfware Home</a></td>
              </tr>
            </table>
          </div>
        </td>
      </tr>
    <tr height="10%">
      <td bgcolor="#ddddff">
        <div id="feedback" title="operation feedback">
          --- operation feedback ---
          </div>
        </td>
      </tr>
    <tr height="10%">
      <td bgcolor="#ddffdd">
        Customize the interactions with the <b>graph</b>.
        <br/>
        <input type="button" onclick="clusterByGroups()"  value="Cluster by group type">
        <input type="button" onclick="clusterByHubsize()" value="Cluster by group size">
        <input type="button" onclick="clusterExpand()"    value="Expand all clusters">
        </br>
        <input type="checkbox" name="physics"    class="physics"    onclick="switchPhysics()" value="true"  title="activate animation"                   checked>live</input>
        <input type="checkbox" name="removeOld"  class="removeOld"                            value="false" title="activate removal of old nodes"               >remove old</input>
        <br/>
        filter: <input type="text" name="filter" value="" id="filter" title="show only nodes with a string in their label"/>
        </td>
      </tr>
    </table>
  <div id="visnetwork" height="70%">
    --- graph network ---
    </div>
  <script type="text/javascript" src="Actions.js"></script>
  <script type="text/javascript" src="PostProc.js"></script>
  <script type="text/javascript" src="GraphView.js"></script>
  <%  
    Init.init(); 
    String name  = request.getParameter("name");
    String spark = request.getParameter("spark");
    String livy  = request.getParameter("livy");
    String hbase = request.getParameter("hbase");
    // by Bean: WSCommand wsc = new WSCommand();
    wsc.setup(name, spark, livy, hbase);
    String nodesS = wsc.nodes().toJSONArray().toString();
    String edgesS = wsc.edges().toJSONArray().toString();;
   %>
  <script type="text/javascript" src ="resizableTable.js"></script>
  <script type="text/javascript">
    var nodesS  = '<%=nodesS%>';
    var edgesS  = '<%=edgesS%>';
    document.getElementById("feedback").innerHTML += "<br/>Loading servers";
    show(nodesS, edgesS);
    </script>
  </body>
