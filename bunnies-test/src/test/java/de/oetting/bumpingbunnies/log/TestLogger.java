package de.oetting.bumpingbunnies.log;

public class TestLogger implements de.oetting.bumpingbunnies.logger.Logger {

	private final Class<?> clazz;

	@Override
	public void info(String log, Object... params) {
		System.out.print("Info " + String.format(log, params));
	}

	public TestLogger(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public void debug(String log, Object... params) {
		System.out.print(clazz + ": Debug " + String.format(log, params));
	}

	@Override
	public void verbose(String log, Object... params) {
		System.out.print(clazz + ": Verbose " + String.format(log, params));
	}

	@Override
	public void warn(String string, Object... params) {
		System.out.print(clazz + ": Warn " + String.format(string, params));
	}

	@Override
	public void warn(String string, Throwable t, Object... params) {
		System.out.print(clazz + ": Warn " + String.format(string, params));
		t.printStackTrace();
	}

	@Override
	public void error(String string, Object... params) {
		System.out.print(clazz + ": Error " + String.format(string, params));
	}

	@Override
	public void error(String string, Throwable t, Object... params) {
		System.out.print(clazz + ": Error " + String.format(string, params));
		t.printStackTrace();
	}

	@Override
	public boolean isInfoEnabled() {
		return true;
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
		return true;
	}

	@Override
	public boolean isErrorEnabled() {
		return true;
	}

}
