package de.oetting.bumpingbunnies.model.game.objects;

import de.oetting.bumpingbunnies.model.color.Color;

public class Water implements GameObjectWithImage {

	private final Rect rect;
	private ImageWrapper bitmap;

	public Water(long minX, long minY, long maxX, long maxY) {
		this.rect = new Rect(minX, maxX, minY, maxY);
	}

	public Water(Rect rect) {
		this.rect = rect;
	}

	@Override
	public long maxX() {
		return this.rect.getMaxX();
	}

	@Override
	public long maxY() {
		return this.rect.getMaxY();
	}

	@Override
	public long minX() {
		return this.rect.getMinX();
	}

	@Override
	public long minY() {
		return this.rect.getMinY();
	}

	@Override
	public int getColor() {
		return Color.BLUE & 0x88FFFFFF;
	}

	@Override
	public int accelerationOnThisGround() {
		return ModelConstants.ACCELERATION_X_WATER;
	}

	@Override
	public ImageWrapper getBitmap() {
		return this.bitmap;
	}

	@Override
	public void setBitmap(ImageWrapper b) {
		this.bitmap = b;
	}

}
