package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class CollisionDetectionFactory {

	public static CollisionDetection create(World world) {
		CollisionDetection collisionDetection = new CollisionDetection(world);
		return collisionDetection;
	}
}
