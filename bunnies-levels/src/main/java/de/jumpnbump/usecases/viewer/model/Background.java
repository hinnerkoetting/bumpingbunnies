package de.jumpnbump.usecases.viewer.model;

public class Background extends FixedWorldObject {

	public Background(int id, int minX, int minY, int maxX, int maxY) {
		super(id, minX, minY, maxX, maxY, Color.WHITE);
	}

	@Override
	public int accelerationOnThisGround() {
		return 0;
	}

}
