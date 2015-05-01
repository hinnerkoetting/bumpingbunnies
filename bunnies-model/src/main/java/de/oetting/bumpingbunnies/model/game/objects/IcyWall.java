package de.oetting.bumpingbunnies.model.game.objects;

public class IcyWall extends FixedWorldObject implements ModelConstants {

	public IcyWall(int id, long minX, long minY, long maxX, long maxY) {
		super(id, minX, minY, maxX, maxY, 0xff8888ff);
	}

	public IcyWall(IcyWall other) {
		this(other.id(), other.minX(), other.minY(), other.maxX(), other.maxY());
		setBitmap(other.getBitmap());
		setzIndex(other.getzIndex());
	}

	@Override
	public int accelerationOnThisGround() {
		return ModelConstants.ACCELERATION_X_ICE;
	}

}
