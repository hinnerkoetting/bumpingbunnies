package de.jumpnbump.logger;

public class Logger {

	static Level globalLogLevel = Level.INFO;

	public static MyLog getLogger(Class<?> cl) {
		return new AndroidLog(cl);
	}

	public static void setGlobalLogLevel(Level level) {
		globalLogLevel = level;
	}
}
