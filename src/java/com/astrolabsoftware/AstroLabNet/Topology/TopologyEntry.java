package com.astrolabsoftware.AstroLabNet.Journal;

/** The <em>TopologyEntry</em> represents one <em>Topology</em> entry.
  * To be used in <em>JavaFX</em> {@link TableView}.
  * (It is a <em>JavaBean</em>.) 
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
// TBD: use proper (nonString) types in constructor
// TBD: add timestamp
public class TopologyEntry {
  
  public TopologyEntry() {}
  
  public TopologyEntry(String key,
                       String name,
                       String location,
                       String spark,
                       String livy,
                       String hbase,
                       String comment) {
    _key      = key;
    _name     = name;
    _location = location;
    _spark    = spark;
    _livy     = livy;
    _hbase    = hbase;
    _comment  = comment;
    }
    
  public void setKey(String key) {
    _key = key;
    }
  
  public void setName(String name) {
    _name = name;
    }
  
  public void setLocation(String location) {
    _location = location;
    }
  
  public void setSpark(String spark) {
    _spark = spark;
    }
  
  public void setLivy(String livy) {
    _livy = livy;
    }
    
  public void setHbase(String hbase) {
    _hbase = hbase;
    }
    
  public void setComment(String comment) {
    _comment = comment;
    }
    
  public String getKey() {
    return _key;
    }
  
  public String getName() {
    return _name;
    }
  
  public String getLocation() {
    return _location;
    }
  
  public String getSpark() {
    return _spark;
    }
  
  public String getLivy() {
    return _livy;
    }
  
  public String getHbase() {
    return _hbase;
    }
   
  public String getComment() {
    return _comment;
    }
  
  private String _key      = null;
  private String _name     = null;
  private String _location = null;
  private String _spark    = null;
  private String _livy     = null;
  private String _hbase    = null;
  private String _comment  = null;
 
 }
