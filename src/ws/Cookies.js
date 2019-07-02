// Cookies Management
// ==================

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
      edges.push({"from":"Group:Actions and Jobs",
                  "to":node.id,
                  "label":" ",
                  "title":" ",
                  "group":" ",
                  "color":" ",
                  "arrows":"to",
                  "value":0});
      }
    }
  }

// Set Action Cookie
function setActionCookies() {
  var name = document.getElementById("actionName").value;
  var cmd  = document.getElementById("script").value;
  var lang = document.getElementById("actionLanguage").value.toUpperCase();
  var node = {"id":"Action:" + name,
              "type":"Action",
              "label":name,
              "title":name + " (" + lang + ")",
              "color":" ",
              "group":"ActionJob",
              "value":0,
              "language":lang,
              "cmd":btoa(cmd)};              
  setCookie("Action." + name + "=" + escape(JSON.stringify(node)),  365);
  document.getElementById("feedback").innerHTML = "Recording Action " + name;
  }
  
// Remove Action Cookie
function rmActionCookies() {
  var name = document.getElementById("actionName").value;
  document.cookie = "Action." + name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
  removeNode("Action:" + name);
  document.getElementById("feedback").innerHTML = "Removing Action " + name;
  }

// Set Job Cookie
function setJobCookies() {
  var name      = document.getElementById("jobName").value;
  var className = document.getElementById("className").value;
  var file      = document.getElementById("jarName").value;
  var args      = document.getElementById("args").value;
  var node = {"id":"Job:" + name,
              "type":"Job",
              "group":"ActionJob",
              "color":" ",
              "label":name,
              "title":name + " (" + className + " from " + file + ", using " + args + ")",
              "value":0,              
              "file":file,
              "className":className,
              "args":args,
              "driverMemory":document.getElementById("driverMemory").value,
              "driverCores":document.getElementById("driverCores").value,
              "executorMemory":document.getElementById("executorMemory").value,
              "executorCores":document.getElementById("executorCores").value};
  setCookie("Job." + name + "=" + escape(JSON.stringify(node)),  365);
  document.getElementById("feedback").innerHTML = "Recording Job " + name;
  }
  
// Remove Job Cookie
function rmJobCookies() {
  var name = document.getElementById("jobName").value;
  document.cookie = "Job." + name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
  removeNode("Job:" + name);
  document.getElementById("feedback").innerHTML = "Removing Job " + name;
  }
