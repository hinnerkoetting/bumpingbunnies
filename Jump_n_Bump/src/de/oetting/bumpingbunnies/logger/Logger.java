package de.oetting.bumpingbunnies.logger;

public class Logger {

	static Level globalLogLevel = Level.INFO;

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
