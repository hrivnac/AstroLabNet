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
      var opts = node.id.split(":")[1].split(" ")
      html += "<table>";
      html += "  <tr>";
      html += "    <td>";
      html += "      <textarea rows='5' cols='50' name='script' form='session' id='script'>";
      html += "        </textarea>";
      html += "      </td>";
      html += "    <td>";
      html += "      <form action='Session.jsp' id='session' target='RESULT'>";
      html += "        <input type='hidden' name='name' id='actionName'>";
      html += "        <input type='hidden' name='language' value='" + opts[0] + "'>";
      html += "        <input type='hidden' name='server'   value='" + opts[3] + "'>";
      html += "        <input type='submit' value='Execute'>";
      html += "        </form>";
      html += "      </td>";
      html += "    </tr>";
      html += "  </table>";
      break;
    case "Sender":
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
      return "document.getElementById('script').innerHTML = atob('" + node.cmd + "');"
           + "document.getElementById('actionName').value = '" + node.label + "';";
      break;
    default:
      return null;
      break;     
    }
  }
