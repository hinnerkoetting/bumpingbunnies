package de.oetting.bumpingbunnies.model.game.objects;

import de.oetting.bumpingbunnies.model.color.Color;

public class Jumper extends FixedWorldObject {

	public Jumper(int id, long minX, long minY, long maxX, long maxY) {
		super(id, minX, minY, maxX, maxY, Color.YELLOW);
	}

	public Jumper(Jumper other) {
		this(other.id(), other.minX(), other.minY(), other.maxX(), other.maxY());
		setBitmap(other.getBitmap());
		setzIndex(other.getzIndex());
	}

	@Override
	public int accelerationOnThisGround() {
		return ModelConstants.ACCELERATION_X_JUMPER;
	}

}
