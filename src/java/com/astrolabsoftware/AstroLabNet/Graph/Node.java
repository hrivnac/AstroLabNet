package com.astrolabsoftware.AstroLabNet.Graph;

// org.json
import org.json.JSONObject;

// Log4J
import org.apache.log4j.Logger;

/** <code>Node</code> is {@link JSONObject} representation of
  * <a href="http://visjs.org">vis.js</a> <em>Node</em>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class Node extends JSONObject
                  implements Comparable<Node> {
    
  /** Create.
    * @param type     The Node type.
    * @param label    The Node label.
    * @param title    The Node title.
    * @param subtitle The Node subtitle.
    * @param group    The group, to which this Node belongs.
    * @param shape    The Node shape.
    * @param value    The Node value. */
  public Node(String type,
              String label,
              String title,
              String subtitle,
              String group,
              String shape,
              String value) {
    super();
    put("id",       type + ":" + label);
    put("label",    label);
    put("title",    type + ":</br>" + title + "</br>" + subtitle);
    put("group",    group);
    put("shape",    shape);
    put("value",    value);
    JSONObject colorO = new JSONObject();
    colorO.put("background", "white");
    colorO.put("border",     "black");
    colorO.put("highlight",  "#eeeeee");
    colorO.put("inherit",    false);    
    put("color",    colorO);

    }
    
  @Override
  public int compareTo(Node other) {
    return toString().compareTo(other.toString());
    }
  
  /** Logging . */
  private static Logger log = Logger.getLogger(Node.class);
   
  }
