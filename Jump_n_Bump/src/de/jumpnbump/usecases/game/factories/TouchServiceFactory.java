package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.GameView;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovement;
import de.jumpnbump.usecases.game.businesslogic.TouchService;
import de.jumpnbump.usecases.game.model.World;

public class TouchServiceFactory {

	public static TouchService create(World world, PlayerMovement playerMovent,
			GameView gameView) {
		TouchService touchService = new TouchService(world, playerMovent);
		gameView.addOnSizeListener(touchService);
		return touchService;
	}
}
