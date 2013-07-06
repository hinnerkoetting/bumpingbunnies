package de.oetting.bumpingbunnies.usecases.game.model;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;

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

	int accelerationOnThisGround();

	void interactWithPlayerOnTop(Player p);

	void handleCollisionWithPlayer(Player player,
			CollisionDetection collisionDetection);

}
