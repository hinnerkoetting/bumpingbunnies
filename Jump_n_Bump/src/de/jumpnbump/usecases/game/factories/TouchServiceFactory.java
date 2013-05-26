package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.GameView;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovement;
import de.jumpnbump.usecases.game.businesslogic.TouchService;
import de.jumpnbump.usecases.game.model.World;
import de.jumpnbump.usecases.game.network.GameNetworkSendThread;

public class TouchServiceFactory {

	public static TouchService create(World world, PlayerMovement playerMovent,
			GameView gameView, GameNetworkSendThread networkThread) {
		TouchService touchService = new TouchService(world, playerMovent,
				networkThread);
		gameView.addOnSizeListener(touchService);
		return touchService;
	}
}
