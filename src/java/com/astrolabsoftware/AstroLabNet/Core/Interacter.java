package com.astrolabsoftware.AstroLabNet.Core;

import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;
import com.astrolabsoftware.AstroLabNet.Browser.Actions.*;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.Utils.StringFile;
import com.astrolabsoftware.AstroLabNet.Utils.StringResource;
import com.astrolabsoftware.AstroLabNet.Utils.Init;
import com.astrolabsoftware.AstroLabNet.Utils.AstroLabNetException;
import com.astrolabsoftware.AstroLabNet.Livyser.Language;
import com.astrolabsoftware.AstroLabNet.Core.Interacter;

/** Common interface exposed to am interactive User.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public interface Interacter {
  
  /** Fill the pre-defined {@link Action}s. */
  public void readActions();
  
  /** Add {@link Server}.
    * @param name     The {@link ServerRep} name.
    * @param urlLivy  The url of the <em>Livy</em> server. Should not be <tt>null</tt>.
    * @param urlSpark The url of the <em>Spark</em> server. May be <tt>null</tt>. */
  public void addServer(String name,
                        String urlLivy,
                        String urlSpark);
  
  /** Add {@link ActionRep}.
    * @param name     The {@link ActionRep} name.
    * @param cmd      The command to execute.
    * @param language The {@link Language} of the command. */
  public void addAction(String   name,
                        String   cmd,
                        Language language);
  
  }