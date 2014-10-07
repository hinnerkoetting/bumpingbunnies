package de.oetting.bumpingbunnies.logger;

public class LoggerFormatter implements Logger {

	private final Logger delegate;

	public LoggerFormatter(Logger delegate) {
		this.delegate = delegate;
	}

	@Override
	public void info(String log, Object... params) {
		delegate.info(String.format(log, params));
	}

	@Override
	public void debug(String log, Object... params) {
		delegate.debug(String.format(log, params));
	}

	@Override
	public void verbose(String log, Object... params) {
		delegate.verbose(String.format(log, params));
	}

	@Override
	public void warn(String log, Object... params) {
		delegate.warn(String.format(log, params));
	}

	@Override
	public void warn(String log, Throwable t, Object... params) {
		delegate.warn(String.format(log, params), t);
	}

	@Override
	public void error(String log, Object... params) {
		delegate.info(String.format(log, params));
	}

	@Override
	public void error(String log, Throwable t, Object... params) {
		delegate.error(String.format(log, params), t);
	}

	public boolean isInfoEnabled() {
		return delegate.isInfoEnabled();
	}

	public boolean isDebugEnabled() {
		return delegate.isDebugEnabled();
	}

	public boolean isVerboseEnabled() {
		return delegate.isVerboseEnabled();
	}

	public boolean isWarnEnabled() {
		return delegate.isWarnEnabled();
	}

	public boolean isErrorEnabled() {
		return delegate.isErrorEnabled();
	}

}
