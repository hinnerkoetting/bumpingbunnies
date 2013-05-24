package de.jumpnbump.logger;

import android.util.Log;

public class AndroidLog implements de.jumpnbump.logger.MyLog {

	private String tag;

	public AndroidLog(Class<?> cl) {
		this.tag = cl.getSimpleName();
	}

	@Override
	public void info(String log, Object... params) {
		Log.i(this.tag, String.format(log, params));
	}

	@Override
	public void debug(String log, Object... params) {
		Log.d(this.tag, String.format(log, params));
	}

	@Override
	public void verbose(String log, Object... params) {
		Log.v(this.tag, String.format(log, params));

	}

}
