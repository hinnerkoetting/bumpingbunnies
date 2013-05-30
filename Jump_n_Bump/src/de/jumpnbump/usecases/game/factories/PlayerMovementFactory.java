package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.businesslogic.CollisionDetection;
import de.jumpnbump.usecases.game.businesslogic.InteractionService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class PlayerMovementFactory {

	public static PlayerMovementController create(Player movedPlayer,
			World world) {
		CollisionDetection collisionDetection = new CollisionDetection(world);
		InteractionService interaction = new InteractionService(
				collisionDetection);
		return new PlayerMovementController(movedPlayer, world, interaction,
				collisionDetection);
	}
}
