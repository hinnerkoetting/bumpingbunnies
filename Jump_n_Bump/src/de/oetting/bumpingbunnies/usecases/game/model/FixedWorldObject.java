package de.oetting.bumpingbunnies.usecases.game.model;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionHandling;

public abstract class FixedWorldObject implements GameObject {

	private int id;
	private final Rect rect;
	private final int color;
	private final CollisionHandling collisionHandling;

	public FixedWorldObject(int id, int minX, int minY, int maxX, int maxY,
			int color) {
		this.id = id;
		this.rect = new Rect(minX, maxX, minY, maxY);
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
		return this.rect.getMinX();
	}

	@Override
	public int minY() {
		return this.rect.getMinY();
	}

	@Override
	public int maxX() {
		return this.rect.getMaxX();
	}

	@Override
	public int maxY() {
		return this.rect.getMaxY();
	}

	@Override
	public int id() {
		return this.id;
	}

	@Override
	public void handleCollisionWithPlayer(Player player,
			CollisionDetection collisionDetection) {
		this.collisionHandling.interactWith(player, this, collisionDetection);
	}
}
