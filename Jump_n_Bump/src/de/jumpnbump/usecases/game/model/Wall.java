package de.jumpnbump.usecases.game.model;

import android.graphics.Color;

public class Wall extends FixedWorldObject implements ModelConstants {

	public Wall(int id, int minX, int minY, int maxX, int maxY, int color) {
		super(id, minX, minY, maxX, maxY, color);
	}

	public Wall(int id, int minX, int minY, int maxX, int maxY) {
		this(id, minX, minY, maxX, maxY, Color.GRAY);
	}

	@Override
	public int accelerationOnThisGround() {
		return ModelConstants.ACCELERATION_X_WALL;
	}

	@Override
	public void interactWithPlayerOnTop(Player p) {
	}

}
