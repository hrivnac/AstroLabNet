package com.astrolabsoftware.AstroLabNet.Catalog;

/** The <em>CatalogEntry</em> represents one <em>Catalog</em> entry.
  * To be used in <em>JavaFX</em> {@link TableView}.
  * (It is a <em>JavaBean</em>.) 
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
// TBD: use proper (nonString) types in constructor
// TBD: add timestamp
public class CatalogEntry {
  
  public CatalogEntry() {}
  
  public CatalogEntry(String key,
                      String name) {
    _key      = key;
    _name     = name;
    }
    
  public void setKey(String key) {
    _key = key;
    }
  
  public void setName(String name) {
    _name = name;
    }
      
  public String getKey() {
    return _key;
    }
  
  public String getName() {
    return _name;
    }
  
  private String _key      = null;
  private String _name     = null;
 
  }
