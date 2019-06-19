// Graph options
var options = {
  physics: {
    forceAtlas2Based: {
      gravitationalConstant:-500,
      centralGravity:0.05,
      springLength:20,
      damping: 0.5,
      springConstant:0.18
      },
    maxVelocity:100,
    solver:"forceAtlas2Based",
    timestep:0.25,
    stabilization: {
      iterations:50,
      fit:true
      },
    adaptiveTimestep:true
    },
  layout: {
    improvedLayout:true
    },
  //layout: {
  //  hierarchical: {
  //    direction:"LR",
  //    sortMethod:"directed"
  //    }
  //  },
  nodes: {
    shape:"dot",
    scaling: {
      customScalingFunction: function (min, max, total, value) {
        if (value == 0) {
          return 0.5;
          }
        return value / max;
        },
      }
    },
  edges: {
    smooth: {
      type:"dynamic",
      forceDirection:"none",
      roundness:0.5
      },
    scaling: {
      customScalingFunction: function (min, max, total, value) {
        return (value - min) / (max - min) / 2;
        }
      }
    },
  groups: {
    " ":{color:{border:"black", background:"white"}}
    }
  };
  
