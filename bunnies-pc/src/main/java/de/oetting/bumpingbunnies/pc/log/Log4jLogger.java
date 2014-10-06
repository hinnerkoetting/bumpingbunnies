package de.oetting.bumpingbunnies.pc.log;

import org.apache.log4j.Level;

import de.oetting.bumpingbunnies.logger.Logger;

public class Log4jLogger implements Logger {

	private final org.apache.log4j.Logger logger;

	public Log4jLogger(org.apache.log4j.Logger logger) {
		this.logger = logger;
	}

	@Override
	public void info(String log, Object... params) {
		logger.info(log);
	}

	@Override
	public void debug(String log, Object... params) {
		logger.debug(log);
	}

	@Override
	public void verbose(String log, Object... params) {
		logger.trace(log);
	}

	@Override
	public void warn(String string, Object... params) {
		logger.warn(string);
	}

	@Override
	public void warn(String string, Throwable t, Object... params) {
		logger.warn(string, t);
	}

	@Override
	public void error(String string, Object... params) {
		logger.error(string);
	}

	@Override
	public void error(String string, Throwable t, Object... params) {
		logger.error(string, t);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isVerboseEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isEnabledFor(Level.WARN);
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isEnabledFor(Level.ERROR);
	}

}
