package de.jumpnbump.logger;

public class Logger {

	public static MyLog getLogger(Class<?> cl) {
		return new AndroidLog(cl);
	}
}
