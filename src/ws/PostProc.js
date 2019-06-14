// Post Process

function postProcNode(node) {
  switch (node.id.split(":")[0]) {
    case "Server":
      node.shape = "image";
      node.image = "images/Livy.png";
      break;
    case "Action":
      node.shape = "image";
      node.image = "images/Action.png";
      break;
    case "Job":
      node.shape = "image";
      node.image = "images/Job.png";
      break;
    case "PythonSession":
      node.shape = "image";
      node.image = "images/Session.png";
      break;
    case "ScalaSession":
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
  
