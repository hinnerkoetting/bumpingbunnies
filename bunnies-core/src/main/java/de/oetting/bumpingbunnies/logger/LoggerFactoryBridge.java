package de.oetting.bumpingbunnies.logger;

public abstract class LoggerFactoryBridge {

	protected abstract Logger create(Class<?> clazz);
	
}
