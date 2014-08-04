package de.oetting.bumpingbunnies.android.log;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactoryBridge;

public class AndroidLoggerFactoryBridge extends LoggerFactoryBridge {

	@Override
	protected Logger create(Class<?> clazz) {
		return new AndroidLog(clazz);
	}

}
