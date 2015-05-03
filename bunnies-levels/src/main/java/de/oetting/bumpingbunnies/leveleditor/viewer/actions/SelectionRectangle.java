package de.oetting.bumpingbunnies.leveleditor.viewer.actions;

import de.oetting.bumpingbunnies.model.game.objects.Rectangle;

public class SelectionRectangle implements Rectangle {

	private long minX;
	private long minY;
	private long maxX;
	private long maxY;

	public SelectionRectangle(long minX, long minY, long maxX, long maxY) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	@Override
	public long maxX() {
		return maxX;
	}

	@Override
	public long maxY() {
		return maxY;
	}

	@Override
	public long minX() {
		return minX;
	}

	@Override
	public long minY() {
		return minY;
	}

}
