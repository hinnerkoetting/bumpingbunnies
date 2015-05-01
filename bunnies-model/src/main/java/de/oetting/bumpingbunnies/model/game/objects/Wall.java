package de.oetting.bumpingbunnies.model.game.objects;

import de.oetting.bumpingbunnies.model.color.Color;

public class Wall extends FixedWorldObject implements ModelConstants {

	public Wall(int id, long minX, long minY, long maxX, long maxY, int color) {
		super(id, minX, minY, maxX, maxY, color);
	}

	public Wall(int id, long minX, long minY, long maxX, long maxY) {
		this(id, minX, minY, maxX, maxY, Color.GRAY);
	}

	public Wall(Wall other) {
		this(other.id(), other.minX(), other.minY(), other.maxX(), other.maxY(), other.getColor());
		setBitmap(other.getBitmap());
		setzIndex(other.getzIndex());
	}

	
	@Override
	public int accelerationOnThisGround() {
		return ModelConstants.ACCELERATION_X_WALL;
	}

}
