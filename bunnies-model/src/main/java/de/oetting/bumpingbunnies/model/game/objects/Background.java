package de.oetting.bumpingbunnies.model.game.objects;

public class Background extends FixedWorldObject {

	public Background(int id, long minX, long minY, long maxX, long maxY, int color) {
		super(id, minX, minY, maxX, maxY, color);
	}

	@Override
	public int accelerationOnThisGround() {
		return 0;
	}

}
