package de.oetting.bumpingbunnies.model.configuration;

public enum SpeedMode {
	FAST(30), MEDIUM(25), SLOW(22);
	
	private int speed;

	SpeedMode(int value) {
		this.speed = value;
	}

	public int getSpeed() {
		return speed;
	}
}
