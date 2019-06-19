// Form Actions

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
      var opts = node.id.split(":")[1].split(" ");
      html += "<table>";
      html += "  <tr>";
      html += "    <td>";
      html += "      <textarea rows='5' cols='50' name='script' form='session' id='script'>";
      html += "        </textarea>";
      html += "      </td>";
      html += "    <td>";
      html += "      <form action='Session.jsp' id='session' target='RESULT'>";
      html += "        <input type='hidden' name='name'     id='actionName'>";
      html += "        <input type='hidden' name='language' value='" + opts[0] + "'>";
      html += "        <input type='hidden' name='server'   value='" + opts[3] + "'>";
      html += "        <input type='submit'                 value='Execute' onclick='document.getElementById(\"feedback\").innerHTML += \"<br/>Sending Action to Session\"'>";
      html += "        </form>";
      html += "      </td>";
      html += "    </tr>";
      html += "  </table>";
      break;
    case "Sender":
      var server = node.id.split(":")[1];
      html += "<table>";
      html += "  <tr>";
      html += "    <td>";
      html += "      <form action='Sender.jsp' id='sender' target='RESULT'>";
      html += "        <input type='hidden' name='name'     id='jobName'>";
      html += "        <input type='hidden' name='server'   value='" + server + "'>";
      html += "        <input type='radio'  name='place'    value='local:' checked>local:";
      html += "        <input type='radio'  name='place'    value='hdfs://SERVER'>htfs://SERVER";
      html += "        <input type='radio'  name='place'    value=''>";
      html += "        <input type='text'   name='jarName'  value='' size='40' id='jarName'><hr/>";
      html += "        <table>";
      html += "        <tr><td>className:</td><td><input type='text' name='className' value='' size='40' id='className'></td></tr>";
      html += "        <tr><td>args:</td><td><input type='text' name='args' value='' size='40' id='args'></td></tr>";
      html += "        <tr><td>Driver Memory:</td><td><input type='text' name='driverMemory' value='' size='10' id='driverMemory'></td>";
      html += "            <td>Driver Cores:</td><td><input type='number' name='driverCores' value='3' size='2' id='driverCores'></td></tr>";
      html += "        <tr><td>Executor Memory:</td><td><input type='text' name='executorMemory' value='' size='10' id='executorMemory'></td>";
      html += "            <td>Executor Cores:</td><td><input type='number' name='executorCores' value='3' size='2' id='executorCores'></td></tr>";
      html += "        </table>";
      html += "        <input type='submit' value='Execute' onclick='document.getElementById(\"feedback\").innerHTML += \"<br/>Sending Job to Sender\"'>";
      html += "        </form>";            
      html += "      </td>";
      html += "    </tr>";
      html += "  </table>";
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
           + "document.getElementById('actionName').value = '"      + node.label + "';";
      break;
    case "Job":
      if (document.getElementById("sender") == null) {
        alert("No Sender opened for Job");
        return "";
        }
      return "document.getElementById('jarName').value        = '" + node.file           + "';"
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
  
