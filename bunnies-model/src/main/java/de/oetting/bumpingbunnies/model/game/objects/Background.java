package de.oetting.bumpingbunnies.model.game.objects;

import de.oetting.bumpingbunnies.model.color.Color;

public class Background extends FixedWorldObject {

	public Background(int id, long minX, long minY, long maxX, long maxY) {
		super(id, minX, minY, maxX, maxY, Color.PINK);
	}

	@Override
	public int accelerationOnThisGround() {
		return 0;
	}

}
