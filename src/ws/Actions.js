// Form Actions

function formNodeActions(node) {
  var html = "";
  switch (node.id.split(":")[0]) {
    case "Server":
      console.log(node.title.split("</br>")[2].split());
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
      break;
    case "Sender":
      break;
    case "Source":
      break;
    case "Topology":
      break;
    case "Catalog":
      break;
    case "Journal":
      break;
    default:
      break;     
    }
  return html;
  }
  
function formEdgeActions(node) {
  var html = "";
  return html;
  }
