// Form Actions
// ============
  
function formNodeAction(node) {
  var html = "";
  switch (node.type) {
    case "/":
      html += "<a class='button' href='CommandCenter.jsp' target='COMMAND'>Reload</a>";
      break;
    case "Server":
      var urls = node.title.split("<br/>")[1].split(" ");
      html += "<a class='button' href='" + urls[0] + "' target='RESULT'>Livy</a>&nbsp;";
      html += "<a class='button' href='" + urls[1] + "' target='RESULT'>Spark</a>&nbsp;";
      html += "<a class='button' href='" + urls[2] + "/status/cluster' target='RESULT'>HBase</a>&nbsp;";
      break;
    case "Group":
      if (node.id.split(":")[1] == "Actions and Jobs") {
        html += "<input type='button' value='Reload' onclick='loadCookies(); show(null, null)'>";
        }
      break;
    case "Action":
      break;
    case "Job":
      break;
    case "Task":
      var server   = node.label.split(" ")[2];
      var id       = node.id.split(":")[1].split(" ")[0];
      var language = node.id.split(":")[1].split(" ")[1];
      html += "<a class='button' href='Task.jsp?server=" + server + "&id=" + id + "&language=" + language + "&delete=false' target='RESULT'>Show</a>&nbsp;";
      html += "<a class='button' href='Task.jsp?server=" + server + "&id=" + id + "&language=" + language + "&delete=true'  target='RESULT'>Delete</a>";
      break;
    case "Batch":
      var server = node.label.split(" ")[2];
      var id     = node.id.split(":")[1];
      html += "<a class='button' href='Batch.jsp?server=" + server + "&id=" + id + "&delete=false' target='RESULT'>Show</a>&nbsp;";
      html += "<a class='button' href='Batch.jsp?server=" + server + "&id=" + id + "&delete=true'  target='RESULT'>Delete</a>";
      break;
    case "Session":
      var server   = node.id.split(":")[1].split(" ")[3];
      var language = node.id.split(":")[1].split(" ")[0];
      html += "<form action='Session.jsp' id='session' target='RESULT' method='POST'>";
      html += "  <table>";
      html += "    <tr>";
      html += "      <td>";
      html += "        <textarea rows='5' cols='50' name='script' form='session' id='script'>";
      html += "          </textarea>";
      html += "        </td>";
      html += "      <td valign='top'>";
      html += "        <input type='hidden' name='server'   id='actionServer'   value='" + server + "'>";
      html += "        <input type='hidden' name='language' id='actionLanguage' value='" + language + "'>";
      html += "        <input type='text'   name='name'     id='actionName'><br/>";
      html += "        <input type='submit' value='Execute' onclick='document.getElementById(\"feedback\").innerHTML = \"Sending Action to Session\"'><br/>";
      html += "        <input type='button' value='Record'  onclick='setActionCookies()'><br/>";
      html += "        <input type='button' value='Remove'  onclick='rmActionCookies()'>";
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
      html += "        <input type='hidden' name='server'   id='jobServer' value='" + server + "'>";
      html += "        <table>";
      html += "        <tr><td>JAR/PY file:</td><td><input type='text' name='jarName'  value='' size='60' id='jarName'></td><tr/>";
      html += "        <tr><td>className:</td><td><input type='text' name='className' value='' size='60' id='className'></td></tr>";
      html += "        <tr><td>args:</td><td><input type='text' name='args' value='' size='60' id='args'></td></tr>";
      html += "        <tr><td>Driver Memory:</td><td><input type='text' name='driverMemory' value='' size='5' id='driverMemory'></td>";
      html += "            <td>Driver Cores:</td><td><input type='number' name='driverCores' value='3' size='2' id='driverCores'></td></tr>";
      html += "        <tr><td>Executor Memory:</td><td><input type='text' name='executorMemory' value='' size='5' id='executorMemory'></td>";
      html += "            <td>Executor Cores:</td><td><input type='number' name='executorCores' value='3' size='2' id='executorCores'></td></tr>";
      html += "        </table>";
      html += "        </td>";
      html += "      <td valign='top'>";
      html += "        <input type='text' name='name' id='jobName'><br/>";
      html += "        <input type='submit' value='Execute' onclick='document.getElementById(\"feedback\").innerHTML = \"Sending Job to Sender\"'><br/>";
      html += "        <input type='button' value='Record'  onclick='setJobCookies()'><br/>";
      html += "        <input type='button' value='Remove'  onclick='rmJobCookies()'>";
      html += "        </td>";
      html += "      </tr>";
      html += "    </table>";
      html += "  </form>";            
      break;
    case "Source":
      break;
    case "Topology":
      var hbase = node.title.split(" ")[4];
      html += "<a class='button' href='HBaseTable.jsp?hbase=" + hbase + "&table=astrolabnet.topology.1&columns=name,location,comment' target='RESULT'>Show</a>";
      break;
    case "Catalog":
      var hbase = node.title.split(" ")[4];
      html += "<a class='button' href='HBaseTable.jsp?hbase=" + hbase + "&table=astrolabnet.catalog.1' target='RESULT'>Show</a>";
      break;
    case "Journal":
      var hbase = node.title.split(" ")[4];
      html += "<form action='HBaseTable.jsp' id='hbase' target='RESULT' method='POST'>";
      html += "  <table>";
      html += "    <tr>";
      html += "      <td>";
      html += "        <table>";   
      html += "          <tr><td><b>period</b>          </td><td><input type='text' size='40' name='period' id='date-range-picker'></td></tr>";
      html += "          <tr><td><b>action</b>          </td><td><input name='action'  type='text' size='20' value=''    ></td></tr>";
      html += "          <tr><td><b>result</b>          </td><td><input name='result'  type='text' size='20' value=''    ></td></tr>";
      html += "          <tr><td><b>comment</b>         </td><td><input name='comment' type='text' size='20' value=''   ></td></tr>";
      html += "          </table>";
      html += "        </td>";
      html += "      <td>";
      html += "        <table>";     
      html += "          <tr><td><b>actor</b></td><td><input name='actor' type='radio' value='*' checked>*</td></tr>";
      html += "          <tr><td>            </td><td><input name='actor' type='radio' value='Action'>Action</td></tr>";
      html += "          <tr><td>            </td><td><input name='actor' type='radio' value='Job'>Job</td></tr>";
      html += "          </table>";
      html += "        </td>";  
      html += "      <td>";
      html += "        <table>  ";    
      html += "          <tr><td><b>rc</b>    </td><td><input name='rc'     type='radio'          value='0'            >0       </td></tr>";
      html += "          <tr><td>             </td><td><input name='rc'     type='radio'          value='-1'           >-1      </td></tr>";
      html += "          <tr><td>             </td><td><input name='rc'     type='radio'          value='*'  checked   >*       </td></tr>";
      html += "          </table>";
      html += "        </td>";
      html += "      </tr>";
      html += "    <tr>";
      html += "      <td><input type='submit' value='Search'></td>";
      html += "      <td>&nbsp</td>";
      html += "      <td>&nbsp</td></tr>";
      html += "      </tr>";
      html += "    </table>";
      html += "  <input type='hidden' name='hbase' value='" + hbase + "'>";
      html += "  <input type='hidden' name='table' value='astrolabnet.journal.1'>";
      html += "  <input type='hidden' name='height' value='100'>";
      html += "  <input type='hidden' name='columns' value='actor,action,rc,time,comment,result'>";
      html += "  <input type='hidden' name='filters' value='i:actor,i:action,d:result,c:comment,d:rc'>";
      html += "  </form>";            
      break;
    default:
      break;     
    }
  return html;
  }
  
function formEdgeAction(edge) {
  var html = "";
  return html;
  }
 
function executeNodeAction(node) {
  switch (node.type) {
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
  
function executeEdgeAction(edge) {
  }
  
function executeNodePostAction(node) {
  switch (node.type) {
    case "Journal":
      $(function() {
        $('#date-range-picker').daterangepicker({
          singleDatePicker: false,    
          showDropdowns: true,
          showWeekNumbers: false,
          showISOWeekNumbers: false,
          timePicker: true,
          timePicker24Hour: true,
          timePickerIncrement: 10,
          timePickerSeconds: false,    
          autoApply: true,
          ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
          locale: {
            format: 'DD/MM/YYYY HH:mm'
            },
          linkedCalendars: false,
          autoUpdateInput: true,
          showCustomRangeLabel: true,
          alwaysShowCalendars: true,
          startDate: moment().subtract(6, 'days'),
          endDate: moment(),
          opens: "right",
          drops: "down"
          },
      function(start, end, label) {
         });
      });     
      break;
    default:
      return null;
      break;     
    }
  }
  
function executeEdgePostAction(edge) {
  }
 
function expandNode(data) {
  }
  
