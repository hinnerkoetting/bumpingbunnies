package de.oetting.bumpingbunnies.usecases.game.model;

public interface GameObject {

	int maxX();

	int maxY();

	int minX();

	int minY();

	int getColor();

	int id();

	int centerX();

	int centerY();

	int movementX();

	int movementY();

	void calculateNextSpeed();

	int accelerationOnThisGround();

	void interactWithPlayerOnTop(Player p);

}
