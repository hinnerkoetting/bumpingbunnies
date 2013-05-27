package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.android.input.AiInputService;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.businesslogic.GamePlayerController;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class DummyInputServiceFactory extends AbstractInputServiceFactory {

	@Override
	public InputService create(InformationSupplier reicerThread,
			GamePlayerController movementController, World world) {
		Player otherPlayer = findOtherPlayer(movementController.getPlayer(),
				world);
		return new AiInputService(otherPlayer, movementController);
	}

	private Player findOtherPlayer(Player player, World world) {
		if (player == world.getPlayer1()) {
			return world.getPlayer2();
		} else {
			return world.getPlayer1();
		}
	}
}
