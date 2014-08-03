package de.oetting.bumpingbunnies.usecases.game.model;

import android.graphics.Bitmap;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionHandling;

public abstract class FixedWorldObject implements GameObjectWithImage {

	private int id;
	private final Rect rect;
	private final int color;
	private Bitmap image;

	public FixedWorldObject(int id, long minX, long minY, long maxX, long maxY,
			int color) {
		this.id = id;
		this.rect = new Rect(minX, maxX, minY, maxY);
		this.color = color;
		if (minX > maxX) {
			throw new IllegalArgumentException("minX must be smaller than maxX");
		}
		if (minY > maxY) {
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
	public long minX() {
		return this.rect.getMinX();
	}

	@Override
	public long minY() {
		return this.rect.getMinY();
	}

	@Override
	public long maxX() {
		return this.rect.getMaxX();
	}

	@Override
	public long maxY() {
		return this.rect.getMaxY();
	}

	public int id() {
		return this.id;
	}

	@Override
	public void setBitmap(Bitmap b) {
		this.image = b;
	}

	@Override
	public Bitmap getBitmap() {
		return this.image;
	}
}
