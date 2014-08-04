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
		if (Level.INFO.isBiggerEqualThan(globalLogLevel)) {
			Log.i(this.tag, String.format(log, params));
		}
	}

	@Override
	public void debug(String log, Object... params) {
		if (Level.DEBUG.isBiggerEqualThan(globalLogLevel)) {
			Log.d(this.tag, String.format(log, params));
		}
	}

	@Override
	public void verbose(String log, Object... params) {
		if (Level.VERBOSE.isBiggerEqualThan(globalLogLevel)) {
			Log.v(this.tag, String.format(log, params));
		}
	}

	@Override
	public void warn(String log, Object... params) {
		if (Level.WARN.isBiggerEqualThan(globalLogLevel)) {
			Log.w(this.tag, String.format(log, params));
		}

	}

	@Override
	public void warn(String log, Throwable t, Object... params) {
		if (Level.WARN.isBiggerEqualThan(globalLogLevel)) {
			Log.w(this.tag, String.format(log, params), t);
		}
	}

	@Override
	public void error(String log, Object... params) {
		if (Level.ERROR.isBiggerEqualThan(globalLogLevel)) {
			Log.e(this.tag, String.format(log, params));
		}
	}

	@Override
	public void error(String log, Throwable t, Object... params) {
		if (Level.ERROR.isBiggerEqualThan(globalLogLevel)) {
			Log.e(this.tag, String.format(log, params), t);
		}
	}

}
