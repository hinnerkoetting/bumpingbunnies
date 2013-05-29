package de.jumpnbump.usecases.game.model;

import android.graphics.Color;

public class Wall implements GameObject, ModelConstants {

	private final int id;
	private final double minX;
	private final double minY;
	private int color;
	private double width;
	private double height;

	public Wall(int id, double minX, double minY, double width, double height) {
		this.id = id;
		this.minX = minX;
		this.minY = minY;
		this.width = width;
		this.height = height;
		this.color = Color.GRAY;
	}

	@Override
	public int id() {
		return this.id;
	}

	@Override
	public double maxX() {
		return this.minX + this.width;
	}

	@Override
	public double maxY() {
		return this.minY + this.height;
	}

	@Override
	public double minX() {
		return this.minX;
	}

	@Override
	public double minY() {
		return this.minY;
	}

	@Override
	public double movementX() {
		return 0;
	}

	@Override
	public double movementY() {
		return 0;
	}

	@Override
	public void moveNextStepX() {
	}

	@Override
	public void moveNextStepY() {
	}

	@Override
	public GameObject simulateNextStepX() {
		return this;
	}

	@Override
	public GameObject simulateNextStepY() {
		return this;
	}

	@Override
	public void calculateNextSpeed() {
	}

	@Override
	public int getColor() {
		return this.color;
	}

	@Override
	public String toString() {
		return "Wall [minX=" + this.minX + ", minY=" + this.minY + ", width="
				+ this.width + ", height=" + this.height + "]";
	}

	@Override
	public double centerX() {
		return this.minX + this.width / 2;
	}

	@Override
	public double centerY() {
		return this.minY + this.height / 2;
	}

}
