package com.astrolabsoftware.AstroLabNet.Browser;

// Log4J
import org.apache.log4j.Level;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/** <code>RootFrameAppender</code> appends Log4J messages
  * to the {@link RootFrame}.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class RootFrameAppender extends AppenderSkeleton {

  /** Append {@link LoggingEvent} to the {@link RootFrame}.
    * @param event The {@link RootFrame} to append. */
	protected void append(LoggingEvent event) {
    String loggerName = event.getLoggerName();
    loggerName = loggerName.substring(loggerName.lastIndexOf(".") + 1, loggerName.length());
    if (event.getLevel().equals(Level.INFO)) {
      RootFrame.setText(loggerName + ": " + event.getMessage().toString());
      }
    else if (event.getLevel().isGreaterOrEqual(Level.WARN)) {
      RootFrame.setError(loggerName + ": " + event.getMessage().toString());
      }
    }

  /** Close. */
	public void close() {}

	/** Doesn't require layout. */
	public boolean requiresLayout() {
    return false;
    }

  }

