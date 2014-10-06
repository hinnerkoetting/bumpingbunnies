package de.oetting.bumpingbunnies.logger;

public class NoopLogger implements Logger {

	@Override
	public void info(String log, Object... params) {
	}

	@Override
	public void debug(String log, Object... params) {
	}

	@Override
	public void verbose(String log, Object... params) {
	}

	@Override
	public void warn(String string, Object... params) {
	}

	@Override
	public void error(String string, Object... params) {
	}

	@Override
	public void error(String string, Throwable t, Object... params) {
	}

	@Override
	public void warn(String string, Throwable t, Object... params) {
	}

	@Override
	public boolean isInfoEnabled() {
		return false;
	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public boolean isVerboseEnabled() {
		return false;
	}

	@Override
	public boolean isWarnEnabled() {
		return false;
	}

	@Override
	public boolean isErrorEnabled() {
		return false;
	}

}
