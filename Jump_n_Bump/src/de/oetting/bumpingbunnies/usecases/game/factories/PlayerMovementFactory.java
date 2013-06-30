package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.InteractionService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

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
