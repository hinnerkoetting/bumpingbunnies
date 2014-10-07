package de.oetting.bumpingbunnies.logger;

public class ThreshholdLogger implements Logger {

	private final Logger delegate;

	public ThreshholdLogger(Logger delegate) {
		this.delegate = delegate;
	}

	@Override
	public void info(String log, Object... params) {
		if (delegate.isInfoEnabled()) {
			delegate.info(log, params);
		}
	}

	@Override
	public void debug(String log, Object... params) {
		if (delegate.isDebugEnabled()) {
			delegate.debug(log, params);
		}
	}

	@Override
	public void verbose(String log, Object... params) {
		if (delegate.isVerboseEnabled()) {
			delegate.verbose(log, params);
		}
	}

	@Override
	public void warn(String log, Object... params) {
		if (delegate.isWarnEnabled()) {
			delegate.warn(log, params);
		}
	}

	@Override
	public void warn(String log, Throwable t, Object... params) {
		if (delegate.isWarnEnabled()) {
			delegate.warn(log, params);
		}
	}

	@Override
	public void error(String log, Object... params) {
		if (delegate.isErrorEnabled()) {
			delegate.error(log, params);
		}
	}

	@Override
	public void error(String log, Throwable t, Object... params) {
		if (delegate.isErrorEnabled()) {
			delegate.error(log, t, params);
		}
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
