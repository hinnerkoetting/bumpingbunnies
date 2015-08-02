package de.oetting.bumpingbunnies.logger;

/**
 * Prints Logging to Sysout.<br>
 * It is used when we do want to use log4j.<br>
 * Logger which is used for tests and build plugins.
 */
public class SysoutLogger implements de.oetting.bumpingbunnies.logger.Logger {

	private final Class<?> clazz;

	@Override
	public void info(String log, Object... params) {
		System.out.println("Info " + String.format(log, params));
	}

	public SysoutLogger(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public void debug(String log, Object... params) {
		System.out.println(clazz + ": Debug " + String.format(log, params));
	}

	@Override
	public void verbose(String log, Object... params) {
		System.out.println(clazz + ": Verbose " + String.format(log, params));
	}

	@Override
	public void warn(String string, Object... params) {
		System.out.println(clazz + ": Warn " + String.format(string, params));
	}

	@Override
	public void warn(String string, Throwable t, Object... params) {
		System.out.println(clazz + ": Warn " + String.format(string, params));
		t.printStackTrace();
	}

	@Override
	public void error(String string, Object... params) {
		System.out.println(clazz + ": Error " + String.format(string, params));
	}

	@Override
	public void error(String string, Throwable t, Object... params) {
		System.out.println(clazz + ": Error " + String.format(string, params));
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
