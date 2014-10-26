package de.oetting.bumpingbunnies.model.game.objects;

public interface GameObject {

	long maxX();

	long maxY();

	long minX();

	long minY();

	int accelerationOnThisGround();

}
