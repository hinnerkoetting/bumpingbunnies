package de.oetting.bumpingbunnies.model.game.objects;

public class Rect {

	private long minX;
	private long maxX;
	private long minY;
	private long maxY;

	public Rect() {
	}

	public Rect(long minX, long maxX, long minY, long maxY) {
		super();
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}

	public long getMinX() {
		return this.minX;
	}

	public void setMinX(long minX) {
		this.minX = minX;
	}

	public long getMaxX() {
		return this.maxX;
	}

	public void setMaxX(long maxX) {
		this.maxX = maxX;
	}

	public long getMinY() {
		return this.minY;
	}

	public void setMinY(long minY) {
		this.minY = minY;
	}

	public long getMaxY() {
		return this.maxY;
	}

	public void setMaxY(long maxY) {
		this.maxY = maxY;
	}

	@Override
	public Rect clone() {
		return new Rect(minX, maxX, minY, maxY);
	}

	public void setCenterX(long gameX) {
		long width = maxX - minX;
		minX = gameX - width / 2;
		maxX = gameX + width / 2;
	}

	public void setCenterY(long gameY) {
		long height = maxY - minY;
		minX = gameY - height / 2;
		maxX = gameY + height / 2;
	}
}
