package de.oetting.bumpingbunnies.logger;

import java.io.InputStream;
import java.util.Properties;

public class LoggerFactory {

	private static LoggerFactoryBridge bridge;

	public static Logger getLogger(Class<?> cl) {
		return new LoggerFormatter(createLoggerFromBridge(cl));
	}

	private static Logger createLoggerFromBridge(Class<?> cl) {
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
			InputStream inputStream = LoggerFactory.class.getResourceAsStream("/de/oetting/bumpingbunnies/log/logging.properties");
			if (inputStream == null) {
				System.err.println("Konnte Logger nicht lesen.");
				bridge = new NoopLoggerFactoryBridge();
				return;
			}
			properties.load(inputStream);
			Class<?> clazz = Class.forName(properties.getProperty("logger"));
			bridge = (LoggerFactoryBridge) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
