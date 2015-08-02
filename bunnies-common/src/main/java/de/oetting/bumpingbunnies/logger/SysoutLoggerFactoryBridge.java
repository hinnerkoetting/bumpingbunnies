package de.oetting.bumpingbunnies.logger;


public class SysoutLoggerFactoryBridge extends LoggerFactoryBridge {

	@Override
	protected Logger create(Class<?> clazz) {
		return new SysoutLogger(clazz);
	}

}
