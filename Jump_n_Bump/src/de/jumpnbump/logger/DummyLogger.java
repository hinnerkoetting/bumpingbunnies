package de.jumpnbump.logger;

public class DummyLogger implements MyLog {

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

}
