<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- AstroLabNet Right-->
<!-- @author Julius.Hrivnac@cern.ch  -->

<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Init" %>
<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Info" %>

<!--%@ page errorPage="ExceptionHandler.jsp" %-->

<head>
  <script type="text/javascript" src="vis-4.21.0/dist/vis.js"></script>
  <script type="text/javascript" src="Options.js"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
  <link href="vis-4.21.0/dist/vis-network.min.css" rel="stylesheet" type="text/css"/>  
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/start/jquery-ui.css" rel="stylesheet"  type="text/css"/>
  <link href="Right.css" rel="stylesheet"  type="text/css"/>
  </head>
  
<body>
  <table width="100%" height="30%" id="Table">
    <tr height="20%">
      <td bgcolor="#ddffdd" height="20%">
        <div id="commands" title="commands">
          --- commands ---
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
    <tr height="10%">
      <td bgcolor="#ddddff">
        <div id="feedback" title="operation feedback">
          --- operation feedback ---
          </div>
        </td>
      </tr>
    </table>
  <div id="visnetwork">
    --- graph network ---
    </div>
  <script type="text/javascript" src="Actions.js"></script>
  <script type="text/javascript" src="PostProc.js"></script>
  <script type="text/javascript" src="GraphView.js"></script>
  <%  
    Init.init(); 
    String element = "xxx";
    String nodesS = "";
    String edgesS = "";
   %>
  <script type="text/javascript" src ="resizableTable.js"></script>
  <script type="text/javascript">
    var element = "<%=element%>";
    var nodesS  = '<%=nodesS%>';
    var edgesS  = '<%=edgesS%>';
    if (element != "null") {
      document.getElementById("feedback").innerHTML += "<br/>Loading " + element + " " + id;
      show(nodesS, edgesS);
      }      
    </script>
  </body>
