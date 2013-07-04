package de.oetting.bumpingbunnies.logger;

import android.util.Log;

public class AndroidLog implements de.oetting.bumpingbunnies.logger.Logger {

	private String tag;

	public AndroidLog(Class<?> cl) {
		this.tag = cl.getSimpleName();
	}

	@Override
	public void info(String log, Object... params) {
		if (Level.INFO.isBiggerEqualThan(LoggerFactory.globalLogLevel)) {
			Log.i(this.tag, String.format(log, params));
		}
	}

	@Override
	public void debug(String log, Object... params) {
		if (Level.DEBUG.isBiggerEqualThan(LoggerFactory.globalLogLevel)) {
			Log.d(this.tag, String.format(log, params));
		}
	}

	@Override
	public void verbose(String log, Object... params) {
		if (Level.VERBOSE.isBiggerEqualThan(LoggerFactory.globalLogLevel)) {
			Log.v(this.tag, String.format(log, params));
		}
	}

	@Override
	public void warn(String log, Object... params) {
		if (Level.WARN.isBiggerEqualThan(LoggerFactory.globalLogLevel)) {
			Log.w(this.tag, String.format(log, params));
		}

	}

	@Override
	public void error(String log, Object... params) {
		if (Level.ERROR.isBiggerEqualThan(LoggerFactory.globalLogLevel)) {
			Log.e(this.tag, String.format(log, params));
		}
	}

	@Override
	public void error(String log, Throwable t, Object... params) {
		if (Level.ERROR.isBiggerEqualThan(LoggerFactory.globalLogLevel)) {
			Log.e(this.tag, String.format(log, params), t);
		}
	}
}
