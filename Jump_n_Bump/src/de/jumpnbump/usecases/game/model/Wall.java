package de.jumpnbump.usecases.game.model;

import android.graphics.Color;

public class Wall implements GameObject, ModelConstants {

	private final int id;
	private final double centerX;
	private final double centerY;
	private int color;
	private double width;
	private double height;

	public Wall(int id, double centerX, double centerY, double width,
			double height) {
		this.id = id;
		this.centerX = centerX;
		this.centerY = centerY;
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
		return this.centerX + this.width / 2;
	}

	@Override
	public double maxY() {
		return this.centerY + this.height / 2;
	}

	@Override
	public double minX() {
		return this.centerX - this.width / 2;
	}

	@Override
	public double minY() {
		return this.centerY - this.height / 2;
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

}
