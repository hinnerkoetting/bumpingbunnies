package de.oetting.bumpingbunnies.model.game.objects;

public class BunnyImageModel {

	private final int headCenterX;
	private final int headCenterY;
	private final int radius;
	private final int width;
	private final int height;

	public BunnyImageModel(int headCenterX, int headCenterY, int radius, int width, int height) {
		this.headCenterX = headCenterX;
		this.headCenterY = headCenterY;
		this.radius = radius;
		this.width = width;
		this.height = height;
	}

	public int getHeadCenterX() {
		return headCenterX;
	}

	public int getHeadCenterY() {
		return headCenterY;
	}

	public int getRadius() {
		return radius;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	

}
