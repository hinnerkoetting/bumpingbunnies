package de.oetting.bumpingbunnies.model.game.objects;

import de.oetting.bumpingbunnies.model.color.Color;

public class IcyWall extends FixedWorldObject implements ModelConstants {

	public IcyWall(int id, long minX, long minY, long maxX, long maxY) {
		super(id, minX, minY, maxX, maxY, Color.WHITE);
	}

	public IcyWall(IcyWall other) {
		this(other.id(), other.minX(), other.minY(), other.maxX(), other.maxY());
		setBitmap(other.getBitmap());
		setZIndex(other.getZIndex());
	}

	@Override
	public int accelerationOnThisGround() {
		return ModelConstants.ACCELERATION_X_ICE;
	}

}
