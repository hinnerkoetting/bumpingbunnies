package de.oetting.bumpingbunnies.usecases.game.model;

public interface GameObject {

	long maxX();

	long maxY();

	long minX();

	long minY();

	int getColor();

	int accelerationOnThisGround();

	void interactWithPlayerOnTop(Player p);

}
