package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.businesslogic.CollisionDetection;
import de.jumpnbump.usecases.game.model.World;

public class CollisionDetectionFactory {

	public static CollisionDetection create(World world) {
		CollisionDetection collisionDetection = new CollisionDetection(world);
		return collisionDetection;
	}
}
