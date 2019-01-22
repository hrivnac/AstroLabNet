package com.astrolabsoftware.AstroLabNet.Utils;

// Java
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/** <code>StringResource</code> gives Resource as String.
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a>
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class StringResource {
    
  /** Create.
    * @param resource The resource path. 
    * @throws EIHadoopException If resource can't be read. */      
  public StringResource(String resource) throws EIHadoopException {
    InputStream       is  = StringResource.class.getClassLoader().getResourceAsStream(resource);
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader    br  = new BufferedReader(isr);
    StringBuffer buffer = new StringBuffer();
    try {
      while (br.ready()) {
        buffer.append(br.readLine() + "\n");
        }
      br.close();
      isr.close();
      is.close();
      }
    catch (IOException e) {
      throw new EIHadoopException("Resource " + resource + " cannot be read !", e);
      }
    _content = buffer.toString();
    }

  /** Give the contained {@link String}.
    * @return The contained {@link String}. */
  public String toString() {
    return _content;
    }

  /** Write the contained {@link String} to a file.
    * @param fn The filename to write the content 
    * @throws EIHadoopException If resource can't be read. */      
  public void toFile(String fn) throws EIHadoopException {
    try {
      FileWriter fstream = new FileWriter(fn);
      BufferedWriter out = new BufferedWriter(fstream);
      out.write(_content);
      out.close();
      }
    catch (Exception e){
      throw new EIHadoopException("Cannot write into file " + fn + " !", e);
      }
    }
     
  private String _content = "";   
                                                
  }
