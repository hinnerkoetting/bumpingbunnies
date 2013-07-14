package de.jumpnbump.usecases.viewer.model;

public class IcyWall extends Wall {

	public IcyWall(int id, int minX, int minY, int maxX, int maxY) {
		super(id, minX, minY, maxX, maxY, 0xff8888ff);
	}

	@Override
	public int accelerationOnThisGround() {
		return ModelConstants.ACCELERATION_X_ICE;
	}

}
