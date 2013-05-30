package de.jumpnbump.usecases.game.model;

import android.graphics.Color;

public class Wall implements GameObject, ModelConstants {

	private final int id;
	private final int minX;
	private final int minY;
	private int color;
	private int maxX;
	private int maxY;

	public Wall(int id, int minX, int minY, int maxX, int maxY) {
		this(id, minX, minY, maxX, maxY, Color.GRAY);
	}

	public Wall(int id, int minX, int minY, int maxX, int maxY, int color) {
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
	public int id() {
		return this.id;
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
	public int minX() {
		return this.minX;
	}

	@Override
	public int minY() {
		return this.minY;
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
	public String toString() {
		return "Wall [id=" + this.id + ", minX=" + this.minX + ", minY="
				+ this.minY + ", color=" + this.color + ", maxX=" + this.maxX
				+ ", maxY=" + this.maxY + "]";
	}

	@Override
	public void setColor(int color) {
		this.color = color;
	}

}
