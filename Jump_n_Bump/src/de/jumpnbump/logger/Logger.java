package de.jumpnbump.logger;

public class Logger {

	static Level globalLogLevel = Level.OFF;

	public static MyLog getLogger(Class<?> cl) {
		if (cl.getResource("/res") != null) {
			return new DummyLogger();
		} else {
			return new AndroidLog(cl);
		}
	}

	public static void setGlobalLogLevel(Level level) {
		globalLogLevel = level;
	}
}
