package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.businesslogic.CollisionDetection;
import de.jumpnbump.usecases.game.businesslogic.GamePlayerController;
import de.jumpnbump.usecases.game.model.Player;

public class PlayerMovementFactory {

	public static GamePlayerController create(Player movedPlayer,
			CollisionDetection collisionDetection) {
		return new GamePlayerController(movedPlayer, collisionDetection);
	}
}
