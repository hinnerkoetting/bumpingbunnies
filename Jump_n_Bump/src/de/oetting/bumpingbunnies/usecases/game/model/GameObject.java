package de.oetting.bumpingbunnies.usecases.game.model;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;

public interface GameObject {

	long maxX();

	long maxY();

	long minX();

	long minY();

	int getColor();

	int accelerationOnThisGround();

	void interactWithPlayerOnTop(Player p);

	void handleCollisionWithPlayer(Player player,
			CollisionDetection collisionDetection);

}
