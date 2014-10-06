package de.oetting.bumpingbunnies.logger;

public interface Logger {

	void info(String log, Object... params);

	void debug(String log, Object... params);

	void verbose(String log, Object... params);

	void warn(String log, Object... params);

	void warn(String log, Throwable t, Object... params);

	void error(String log, Object... params);

	void error(String log, Throwable t, Object... params);

	boolean isInfoEnabled();

	boolean isDebugEnabled();

	boolean isVerboseEnabled();

	boolean isWarnEnabled();

	boolean isErrorEnabled();
}
