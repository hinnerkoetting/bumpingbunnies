package de.jumpnbump.usecases.game.android.input.ai;

import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.factories.AbstractInputServiceFactory;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class NormalAiInputFactory extends AbstractInputServiceFactory {

	@Override
	public InputService create(InformationSupplier reicerThread,
			PlayerMovementController movementController, World world) {
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
