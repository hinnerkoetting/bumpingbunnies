package de.oetting.bumpingbunnies.usecases.game.model;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionHandling;

public abstract class FixedWorldObject implements GameObject {

	private int id;
	private final int minX;
	private final int minY;
	private final int maxX;
	private final int maxY;
	private final int color;
	private final CollisionHandling collisionHandling;

	public FixedWorldObject(int id, int minX, int minY, int maxX, int maxY,
			int color) {
		this.id = id;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.color = color;
		this.collisionHandling = new CollisionHandling();
		if (minX >= maxX) {
			throw new IllegalArgumentException("minX must be smaller than maxX");
		}
		if (minY >= maxY) {
			throw new IllegalArgumentException("minY must be smaller than maxY");
		}
	}

	@Override
	public int getColor() {
		return this.color;
	}

	// @Override
	// public int centerX() {
	// return (this.minX + this.maxX) / 2;
	// }
	//
	// @Override
	// public int centerY() {
	// return (this.minY + this.maxY) / 2;
	// }

	@Override
	public int minX() {
		return this.minX;
	}

	@Override
	public int minY() {
		return this.minY;
	}

	@Override
	public int maxX() {
		return this.maxX;
	}

	@Override
	public int maxY() {
		return this.maxY;
	}

	@Override
	public int id() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Wall [id=" + this.id + ", minX=" + this.minX + ", minY="
				+ this.minY + ", color=" + this.color + ", maxX=" + this.maxX
				+ ", maxY=" + this.maxY + "]";
	}

	@Override
	public void handleCollisionWithPlayer(Player player,
			CollisionDetection collisionDetection) {
		this.collisionHandling.interactWith(player, this, collisionDetection);
	}
}
