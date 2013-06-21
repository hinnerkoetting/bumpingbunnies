package de.jumpnbump.usecases.game.model;

import android.graphics.Color;

public class BloodParticle implements GameObject, ModelConstants {

	private MovingGameobjectState movementState;

	public BloodParticle(int centerX, int centerY, int movementX, int movementY) {
		this.movementState = new MovingGameobjectState();
		this.movementState.centerX = centerX;
		this.movementState.centerY = centerY;
		this.movementState.movementX = movementX;
		this.movementState.movementY = movementY;
	}

	@Override
	public int maxX() {
		return this.movementState.centerX + BLOOD_WIDTH / 2;
	}

	@Override
	public int maxY() {
		return this.movementState.centerY + BLOOD_HEIGHT / 2;
	}

	@Override
	public int minX() {
		return this.movementState.centerX - BLOOD_WIDTH / 2;
	}

	@Override
	public int minY() {
		return this.movementState.centerY - BLOOD_HEIGHT / 2;
	}

	@Override
	public int getColor() {
		return Color.RED;
	}

	@Override
	public int id() {
		return -1; // no interaction
	}

	@Override
	public int centerX() {
		return this.movementState.centerX;
	}

	@Override
	public int centerY() {
		return this.movementState.centerY;
	}

	@Override
	public int movementX() {
		return this.movementState.movementX;
	}

	@Override
	public int movementY() {
		return this.movementState.movementY;
	}

	@Override
	public void calculateNextSpeed() {
		this.movementState.movementX /= 0.9;
		this.movementState.movementY /= 0.9;
	}

	@Override
	public int accelerationOnThisGround() {
		return 0;
	}

	@Override
	public void interactWithPlayerOnTop(Player p) {
	}

}
