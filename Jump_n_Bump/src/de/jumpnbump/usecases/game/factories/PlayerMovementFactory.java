package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.businesslogic.CollisionDetection;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.model.Player;

public class PlayerMovementFactory {

	public static PlayerMovementController create(Player movedPlayer,
			CollisionDetection collisionDetection) {
		return new PlayerMovementController(movedPlayer, collisionDetection);
	}
}
