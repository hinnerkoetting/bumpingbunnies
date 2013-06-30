package de.oetting.bumpingbunnies.usecases.game.model;

public class SpawnPoint {

	private int x;
	private int y;

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public SpawnPoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

}
