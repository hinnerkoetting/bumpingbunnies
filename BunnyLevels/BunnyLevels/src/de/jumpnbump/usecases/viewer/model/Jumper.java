package de.jumpnbump.usecases.viewer.model;


public class Jumper extends FixedWorldObject {


	public Jumper(int id, int minX, int minY, int maxX, int maxY) {
		super(id, minX, minY, maxX, maxY, Color.YELLOW);
	}

	@Override 
	public int accelerationOnThisGround() {
		return 0;
	}

}
