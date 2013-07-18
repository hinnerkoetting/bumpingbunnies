package de.jumpnbump.usecases.viewer.model;

import java.awt.image.BufferedImage;

public abstract class FixedWorldObject implements GameObject {

	private int id;
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	private int color;
	private BufferedImage image;

	public FixedWorldObject(int id, int minX, int minY, int maxX, int maxY,
			int color) {
		this.id = id;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.color = color;
		if (minX >= maxX) {
			throw new IllegalArgumentException("minX must be smaller than maxX");
		}
		if (minY >= maxY) {
			throw new IllegalArgumentException("minY must be smaller than maxY");
		}
	}

	@Override
	public int movementX() {
		return 0;
	}

	@Override
	public int movementY() {
		return 0;
	}

	@Override
	public void calculateNextSpeed() {
	}

	@Override
	public int getColor() {
		return this.color;
	}

	@Override
	public int centerX() {
		return (this.minX + this.maxX) / 2;
	}

	@Override
	public int centerY() {
		return (this.minY + this.maxY) / 2;
	}

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
	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	@Override
	public void setMinY(int minY) {
		this.minY = minY;
	}

	@Override
	public int getHeight() {
		return this.maxY - this.minY;
	}

	@Override
	public void setCenterX(int gameX) {
		int currentWidth = this.maxX - this.minX;
		this.minX = gameX - currentWidth / 2;
		this.maxX = gameX + currentWidth / 2;
	}

	@Override
	public void setCenterY(int gameY) {
		int currentHeight = this.maxY - this.minY;
		this.minY = gameY - currentHeight / 2;
		this.maxY = gameY + currentHeight / 2;
	}

	@Override
	public void setMaxX(int newRight) {
		this.maxX = newRight;
	}

	@Override
	public void setMinX(int newLeft) {
		this.minX = newLeft;
	}

	@Override
	public void applyImage(BufferedImage image) {
		this.image = image;
	}

	@Override
	public boolean hasImage() {
		return this.image != null;
	}

	@Override
	public BufferedImage getImage() {
		return this.image;
	}
}
