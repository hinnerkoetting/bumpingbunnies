package de.oetting.bumpingbunnies.android.log;

import android.util.Log;
import de.oetting.bumpingbunnies.logger.Level;

public class AndroidLog implements de.oetting.bumpingbunnies.logger.Logger {

	static Level globalLogLevel = Level.INFO;

	private String tag;

	public AndroidLog(Class<?> cl) {
		this.tag = cl.getSimpleName();
	}

	@Override
	public void info(String log, Object... params) {
		Log.i(this.tag, log);
	}

	@Override
	public void debug(String log, Object... params) {
		Log.d(this.tag, log);
	}

	@Override
	public void verbose(String log, Object... params) {
		Log.v(this.tag, log);
	}

	@Override
	public void warn(String log, Object... params) {
		Log.w(this.tag, log);
	}

	@Override
	public void warn(String log, Throwable t, Object... params) {
		Log.w(this.tag, log, t);
	}

	@Override
	public void error(String log, Object... params) {
		Log.e(this.tag, log);
	}

	@Override
	public void error(String log, Throwable t, Object... params) {
		Log.e(this.tag, log, t);
	}

	@Override
	public boolean isInfoEnabled() {
		return Level.INFO.isBiggerEqualThan(globalLogLevel);
	}

	@Override
	public boolean isDebugEnabled() {
		return Level.DEBUG.isBiggerEqualThan(globalLogLevel);
	}

	@Override
	public boolean isVerboseEnabled() {
		return Level.VERBOSE.isBiggerEqualThan(globalLogLevel);
	}

	@Override
	public boolean isWarnEnabled() {
		return Level.WARN.isBiggerEqualThan(globalLogLevel);
	}

	@Override
	public boolean isErrorEnabled() {
		return Level.ERROR.isBiggerEqualThan(globalLogLevel);
	}

}
