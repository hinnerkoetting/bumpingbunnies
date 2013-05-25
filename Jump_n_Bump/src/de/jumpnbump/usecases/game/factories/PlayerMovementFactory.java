package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.businesslogic.CollisionDetection;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovement;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class PlayerMovementFactory {

	public static PlayerMovement create(World world, Player movedPlayer,
			CollisionDetection collisionDetection) {
		return new PlayerMovement(world, movedPlayer, collisionDetection);
	}
}
