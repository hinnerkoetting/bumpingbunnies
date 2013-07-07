package de.oetting.bumpingbunnies.logger;

public class LoggerFactory {

	static Level globalLogLevel = Level.INFO;

	public static Logger getLogger(Class<?> cl) {
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
