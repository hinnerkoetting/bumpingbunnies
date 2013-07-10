package de.oetting.bumpingbunnies.usecases.game.model;

public class IcyWall extends Wall {

	public IcyWall(int id, long minX, long minY, long maxX, long maxY) {
		super(id, minX, minY, maxX, maxY, 0xff8888ff);
	}

	@Override
	public int accelerationOnThisGround() {
		return ModelConstants.ACCELERATION_X_ICE;
	}

}
