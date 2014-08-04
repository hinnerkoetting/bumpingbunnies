package de.oetting.bumpingbunnies.logger;

import java.util.Properties;

public class LoggerFactory {

	private static LoggerFactoryBridge bridge;

	public static Logger getLogger(Class<?> cl) {
		if (bridge == null) {
			synchronized (LoggerFactory.class) {
				createBridge();
			}
		}
		return bridge.create(cl);
	}

	private static void createBridge() {
		try {
			Properties properties = new Properties();
			properties.load(LoggerFactory.class.getResourceAsStream("/de/oetting/bumpingbunnies/log/logging.properties"));
			Class<?> clazz = Class.forName(properties.getProperty("logger"));
			bridge = (LoggerFactoryBridge) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
