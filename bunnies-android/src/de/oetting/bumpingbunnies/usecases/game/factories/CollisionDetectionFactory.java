package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.world.World;

public class CollisionDetectionFactory {

	public static CollisionDetection create(World world) {
		CollisionDetection collisionDetection = new CollisionDetection(world);
		return collisionDetection;
	}
}
