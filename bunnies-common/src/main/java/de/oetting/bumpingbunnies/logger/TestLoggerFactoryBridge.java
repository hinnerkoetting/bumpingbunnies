package de.oetting.bumpingbunnies.logger;


public class TestLoggerFactoryBridge extends LoggerFactoryBridge {

	@Override
	protected Logger create(Class<?> clazz) {
		return new TestLogger(clazz);
	}

}
