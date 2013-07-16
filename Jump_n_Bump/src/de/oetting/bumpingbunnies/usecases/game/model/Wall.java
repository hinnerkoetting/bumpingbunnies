package de.oetting.bumpingbunnies.usecases.game.model;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Wall extends FixedWorldObject implements ModelConstants {

	private Bitmap bitmap;

	public Wall(int id, long minX, long minY, long maxX, long maxY, int color) {
		super(id, minX, minY, maxX, maxY, color);
		this.bitmap = this.bitmap;
	}

	public Wall(int id, long minX, long minY, long maxX, long maxY) {
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
