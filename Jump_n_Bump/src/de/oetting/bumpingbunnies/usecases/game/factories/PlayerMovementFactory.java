package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerMovementFactory {

	public static PlayerMovementController create(Player movedPlayer) {
		return new PlayerMovementController(movedPlayer);
	}
}
