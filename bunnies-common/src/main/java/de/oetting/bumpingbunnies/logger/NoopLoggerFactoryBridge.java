package de.oetting.bumpingbunnies.logger;

public class NoopLoggerFactoryBridge extends LoggerFactoryBridge {

	@Override
	protected Logger create(Class<?> clazz) {
		return new NoopLogger();
	}

}
