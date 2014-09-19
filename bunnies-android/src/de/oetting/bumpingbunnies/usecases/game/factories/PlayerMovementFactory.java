package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerMovementFactory {

	public static PlayerMovement create(Player movedPlayer) {
		return new PlayerMovement(movedPlayer);
	}
}
