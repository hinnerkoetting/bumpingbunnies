package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.businesslogic.CollisionDetection;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovement;
import de.jumpnbump.usecases.game.model.Player;

public class PlayerMovementFactory {

	public static PlayerMovement create(Player movedPlayer,
			CollisionDetection collisionDetection) {
		return new PlayerMovement(movedPlayer, collisionDetection);
	}
}
