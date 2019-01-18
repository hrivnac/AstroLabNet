package com.astrolabsoftware.AstroLabNet.Utils;

// Java
import java.io.StringWriter;
import java.io.PrintWriter;

public class AstroLabNetException extends Exception {

  public AstroLabNetException() {
    super();
    }

  public AstroLabNetException(String msg) {
    super(msg);
    }

  public AstroLabNetException(Throwable nested) {
    super(nested);
    }

  public AstroLabNetException(String msg,
                           Throwable nested) {
    super(msg, nested);
    }
    
  /** Give full stack trace.
    * @return The full stack trace. */
  public String stackTrace() {
    return stackTrace2String(this);
    }  
    
  /** Convert {@link Exception} stack trace to String.
    * @param e The {@link Exception}.
    * @return  The stack trace as a String. */
  public static String stackTrace2String(Exception e) {
    StringWriter stack = new StringWriter(); 
    e.printStackTrace(new PrintWriter(stack));
    return stack.toString();
    }
    
  }
