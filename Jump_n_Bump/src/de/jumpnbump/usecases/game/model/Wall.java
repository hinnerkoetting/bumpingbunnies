package de.jumpnbump.usecases.game.model;

import android.graphics.Color;

public class Wall implements GameObject, ModelConstants {

	private final int id;
	private final double minX;
	private final double minY;
	private int color;
	private double maxX;
	private double maxY;

	public Wall(int id, double minX, double minY, double maxX, double maxY) {
		this.id = id;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.color = Color.GRAY;
	}

	@Override
	public int id() {
		return this.id;
	}

	@Override
	public double maxX() {
		return this.maxX;
	}

	@Override
	public double maxY() {
		return this.maxY;
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
	public double centerX() {
		return (this.minX + this.maxX) / 2;
	}

	@Override
	public double centerY() {
		return (this.minY + this.maxY) / 2;
	}

}
