package de.oetting.bumpingbunnies.pc.log;

import de.oetting.bumpingbunnies.logger.Logger;

public class Log4jLogger implements Logger {

	private final org.apache.log4j.Logger LOGGER;

	public Log4jLogger(org.apache.log4j.Logger lOGGER) {
		super();
		LOGGER = lOGGER;
	}

	@Override
	public void info(String log, Object... params) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format(log, params));
		}
	}

	@Override
	public void debug(String log, Object... params) {
		LOGGER.debug(log);
	}

	@Override
	public void verbose(String log, Object... params) {
		LOGGER.trace(log);
	}

	@Override
	public void warn(String string, Object... params) {
		LOGGER.warn(string);
	}

	@Override
	public void warn(String string, Throwable t, Object... params) {
		LOGGER.warn(string, t);
	}

	@Override
	public void error(String string, Object... params) {
		LOGGER.error(string);
	}

	@Override
	public void error(String string, Throwable t, Object... params) {
		LOGGER.error(string, t);
	}

}
