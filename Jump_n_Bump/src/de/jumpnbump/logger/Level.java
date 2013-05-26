package de.jumpnbump.logger;

public enum Level {

	VERBOSE(0), DEBUG(1), INFO(2), WARN(3), OFF(4);

	private int level;

	private Level(int level) {
		this.level = level;
	}

	public boolean isBiggerEqualThan(Level other) {
		return this.level >= other.level;
	}
}
