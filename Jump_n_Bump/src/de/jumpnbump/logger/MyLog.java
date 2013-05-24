package de.jumpnbump.logger;

public interface MyLog {

	void info(String log, Object... params);

	void debug(String log, Object... params);

	void verbose(String log, Object... params);
}
