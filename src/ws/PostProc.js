// Post Process
// ============

function postProcNode(node) {
  switch (node.id.split(":")[0]) {
    case "/":
      node.shape = "image";
      node.image = "images/AstroLab.png";
      break;
    case "Server":
      node.shape = "image";
      if (node.label == "LAL") {
        node.image = "images/LAL.png";
        }
      else if (node.label == "Local"){
        node.image = "images/Local.png";
        }
      break;
    case "Action":
      node.shape = "image";
      node.image = "images/Action.png";
      node.group = "ActionJob";
      node.color = " ";
      break;
    case "Job":
      node.shape = "image";
      node.image = "images/Job.png";
      node.group = "ActionJob";
      node.color = " ";
      break;
    case "Session":
      node.shape = "image";
      node.image = "images/Session.png";
      break;
    case "Sender":
      node.shape = "image";
      node.image = "images/Sender.png";
      break;
    case "Source":
      node.shape = "image";
      node.image = "images/Source.png";
      break;
    case "Task":
      node.shape = "image";
      node.image = "images/Task.png";
      break;
    case "Batch":
      node.shape = "image";
      node.image = "images/Batch.png";
      break;
    case "Topology":
      node.shape = "image";
      node.image = "images/Topology.png";
      break;
    case "Catalog":
      node.shape = "image";
      node.image = "images/Catalog.png";
      break;
    case "Journal":
      node.shape = "image";
      node.image = "images/Journal.png";
      break;
    case "cluster":
      var cName = node.id.split(":")[1];
      if (cName == "LAL") {
        node.shape = "image";
        node.image = "images/LAL.png";
        node.shadow = "true";
        }
      else if (cName == "Local") {
        node.shape = "image";
        node.image = "images/Local.png";
        node.shadow = "true";
        }
      break;
    default:
      break;     
    }
  return node;
  }
  
function postProcEdge(edge) {
  switch (edge.label) {
    default:
      break;     
    }
  return edge;
  }
  
