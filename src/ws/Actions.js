// Form Actions
// ============

// Set a Cookie
function setCookie(cookie, exdays) {
  var d = new Date();
  d.setTime(d.getTime() + (exdays*24*60*60*1000));
  var expires = "expires=" + d.toUTCString();
  document.cookie = cookie + ";" + expires;
  }

// Load Cookies into nodes and edges 
function loadCookies() {
  var cs = document.cookie.split(';');
  var node;
  for (var i = 0; i < cs.length; i++) {
    if (cs[i].trim() != "") {
      node = JSON.parse(unescape(cs[i].trim().split("=")[1].trim()));
      removeObjectByKey(nodes, "id", node.id);
      removeObjectByKey(edges, "to", node.id);
      nodes.push(node);
      edges.push({"from":"Group:Actions and Jobs", "to":node.id, "group":" ", "arrow":"to", "value":0});
      }
    }
  }

// Set Action Cookie
function setActionCookies() {
  var name = document.getElementById("actionName").value;
  var node = {"id":"Action:" + name,
              "type":"Action",
              "label":name,
              "title":"Action:<br/>" + name + "<br/>" + name,
              "group":"ActionJob",
              "shape":" ",
              "value":0,
              "cmd":document.getElementById("script").value};              
  setCookie("Action." + name + "=" + escape(JSON.stringify(node)),  365);
  document.getElementById("feedback").innerHTML += "<br/>Recording Action " + name;
  }
  
// Set Job Cookie
function setJobCookies() {
  var name = document.getElementById("jobName").value;
  var node = {"id":"Job:" + name,
              "type":"Job",
              "label":name,
              "title":"Job:<br/>" + name + "<br/>" + name,
              "group":"ActionJob",
              "shape":" ",
              "value":0,              
              "file":document.getElementById("jarName").value,
              "className":document.getElementById("className").value,
              "args":document.getElementById("args").value,
              "driverMemory":document.getElementById("driverMemory").value,
              "driverCores":document.getElementById("driverCores").value,
              "executorMemory":document.getElementById("executorMemory").value,
              "executorCores":document.getElementById("executorCores").value};
  setCookie("Job." + name + "=" + escape(JSON.stringify(node)),  365);
  document.getElementById("feedback").innerHTML += "<br/>Recording Job " + name;
  }
  
function formNodeAction(node) {
  var html = "";
  switch (node.id.split(":")[0]) {
    case "Server":
      var urls = node.title.split("</br>")[2].split(" ");
      html += "<a href='" + urls[0] + "' target='RESULT'>Livy</a>&nbsp;";
      html += "<a href='" + urls[1] + "' target='RESULT'>Spark</a>&nbsp;";
      html += "<a href='" + urls[2] + "/status/cluster' target='RESULT'>HBase</a>&nbsp;";
      break;
    case "Action":
      break;
    case "Job":
      break;
    case "Session":
      var server = node.id.split(":")[1];
      html += "<form action='Session.jsp' id='session' target='RESULT' method='POST'>";
      html += "  <table>";
      html += "    <tr>";
      html += "      <td>";
      html += "        <textarea rows='5' cols='50' name='script' form='session' id='script'>";
      html += "          </textarea>";
      html += "        </td>";
      html += "      <td valign='top'>";
      html += "          <input type='hidden' name='server'   id='actionServer'   value='" + server + "'>";
      html += "          <input type='hidden' name='language' id='actionLanguage' value=''>";
      html += "          <input type='text'   name='name' id='actionName'><br/>";
      html += "          <input type='submit' value='Execute' onclick='document.getElementById(\"feedback\").innerHTML += \"<br/>Sending Action to Session\"'><br/>";
      html += "          <input type='button' value='Record'  onclick='setActionCookies()'>";
      html += "        </td>";
      html += "      </tr>";
      html += "    </table>";
      html += "  </form>";
      break;
    case "Sender":
      var server = node.id.split(":")[1];
      html += "<form action='Sender.jsp' id='sender' target='RESULT' method='POST'>";
      html += "  <table>";
      html += "    <tr>";
      html += "      <td>";
      html += "          <input type='hidden' name='server'   id='jobServer' value='" + server + "'>";
      html += "          <input type='radio'  name='place'    value='local:' checked>local:";
      html += "          <input type='radio'  name='place'    value='hdfs://SERVER'>htfs://SERVER";
      html += "          <input type='radio'  name='place'    value=''>";
      html += "          <input type='text'   name='jarName'  value='' size='40' id='jarName'><r/>";
      html += "          <table>";
      html += "          <tr><td>className:</td><td><input type='text' name='className' value='' size='40' id='className'></td></tr>";
      html += "          <tr><td>args:</td><td><input type='text' name='args' value='' size='40' id='args'></td></tr>";
      html += "          <tr><td>Driver Memory:</td><td><input type='text' name='driverMemory' value='' size='10' id='driverMemory'></td>";
      html += "              <td>Driver Cores:</td><td><input type='number' name='driverCores' value='3' size='2' id='driverCores'></td></tr>";
      html += "          <tr><td>Executor Memory:</td><td><input type='text' name='executorMemory' value='' size='10' id='executorMemory'></td>";
      html += "              <td>Executor Cores:</td><td><input type='number' name='executorCores' value='3' size='2' id='executorCores'></td></tr>";
      html += "          </table>";
      html += "        </td>";
      html += "      <td valign='top'>";
      html += "        <input type='text' name='name' id='jobName'><br/>";
      html += "        <input type='submit' value='Execute' onclick='document.getElementById(\"feedback\").innerHTML += \"<br/>Sending Job to Sender\"'><br/>";
      html += "        <input type='button' value='Record'  onclick='setJobCookies()'>";
      html += "        </td>";
      html += "      </tr>";
      html += "    </table>";
      html += "  </form>";            
      break;
    case "Source":
      break;
    case "Topology":
      var server = node.label.split(" ")[2];
      html += "<a href='HBaseTable.jsp?server=" + server + "&table=topology' target='RESULT'>Show</a>";
      break;
    case "Catalog":
      var server = node.label.split(" ")[2];
      html += "<a href='HBaseTable.jsp?server=" + server + "&table=catalog' target='RESULT'>Show</a>";
      break;
    case "Journal":
      var server = node.label.split(" ")[2];
      html += "<a href='HBaseTable.jsp?server=" + server + "&table=journal' target='RESULT'>Show</a>";
      break;
    default:
      break;     
    }
  return html;
  }
  
function formEdgeAction(node) {
  var html = "";
  return html;
  }
  
function executeNodeAction(node) {
  switch (node.id.split(":")[0]) {
    case "Action":
      if (document.getElementById("session") == null) {
        alert("No Session opened for Action");
        return "";
        }
      return "document.getElementById('script').innerHTML = atob('" + node.cmd   + "');"
           + "document.getElementById('actionName').value =      '" + node.label + "';";
      break;
    case "Job":
      if (document.getElementById("sender") == null) {
        alert("No Sender opened for Job");
        return "";
        }
      return "document.getElementById('jarName').value        = '" + node.file           + "';"
           + "document.getElementById('jobName').value        = '" + node.label          + "';"
           + "document.getElementById('className').value      = '" + node.className      + "';"
           + "document.getElementById('args').value           = '" + node.args           + "';"
           + "document.getElementById('driverMemory').value   = '" + node.driverMemory   + "';"
           + "document.getElementById('driverCores').value    = '" + node.driverCores    + "';"
           + "document.getElementById('executorMemory').value = '" + node.executorMemory + "';"
           + "document.getElementById('executorCores').value  = '" + node.executorCores  + "';"
           + "document.getElementById('jobName').value        = '" + node.label          + "';";
      break;
    default:
      return null;
      break;     
    }
  }
  
