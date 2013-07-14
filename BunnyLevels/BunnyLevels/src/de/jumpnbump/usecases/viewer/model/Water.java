package de.jumpnbump.usecases.viewer.model;

public class Water extends FixedWorldObject {

	public Water(int id, int minX, int minY, int maxX, int maxY) {
		super(id, minX, minY, maxX, maxY, Color.WATER_BLUE);
	}

	@Override
	public int accelerationOnThisGround() {
		return 0;
	}

}
