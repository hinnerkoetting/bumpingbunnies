package de.oetting.bumpingbunnies.log;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactoryBridge;

public class TestLoggerFactoryBridge extends LoggerFactoryBridge {

	@Override
	protected Logger create(Class<?> clazz) {
		return new TestLogger(clazz);
	}

}
