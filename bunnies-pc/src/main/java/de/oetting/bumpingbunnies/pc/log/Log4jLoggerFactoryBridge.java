package de.oetting.bumpingbunnies.pc.log;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactoryBridge;

public class Log4jLoggerFactoryBridge extends LoggerFactoryBridge {

	@Override
	protected Logger create(Class<?> clazz) {
		return new Log4jLogger(org.apache.log4j.Logger.getLogger(clazz));
	}

}
