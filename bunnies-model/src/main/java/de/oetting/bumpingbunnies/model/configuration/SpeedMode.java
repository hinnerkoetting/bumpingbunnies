package de.oetting.bumpingbunnies.model.configuration;

public enum SpeedMode {
	FAST(22), MEDIUM(25), SLOW(30);
	
	private int speed;

	SpeedMode(int value) {
		this.speed = value;
	}

	public int getSpeed() {
		return speed;
	}
}
