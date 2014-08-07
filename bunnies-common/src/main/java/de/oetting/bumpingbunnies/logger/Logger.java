package de.oetting.bumpingbunnies.logger;

public interface Logger {

	void info(String log, Object... params);

	void debug(String log, Object... params);

	void verbose(String log, Object... params); 

	void warn(String string, Object... params);

	void warn(String string, Throwable t, Object... params);

	void error(String string, Object... params);

	void error(String string, Throwable t, Object... params);
}
