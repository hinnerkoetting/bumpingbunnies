package de.oetting.bumpingbunnies.usecases.game.model;

public class SpawnPoint {

	private long x;
	private long y;

	public long getX() {
		return this.x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return this.y;
	}

	public void setY(long y) {
		this.y = y;
	}

	public SpawnPoint(long x, long y) {
		super();
		this.x = x;
		this.y = y;
	}

}
