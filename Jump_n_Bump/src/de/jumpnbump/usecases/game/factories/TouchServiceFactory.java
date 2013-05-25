package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.GameView;
import de.jumpnbump.usecases.game.businesslogic.CollisionDetection;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovement;
import de.jumpnbump.usecases.game.businesslogic.TouchService;
import de.jumpnbump.usecases.game.model.World;

public class TouchServiceFactory {

	public static TouchService create(World world, GameView gameView) {
		CollisionDetection collisionDetection = CollisionDetectionFactory
				.create(world, gameView);
		PlayerMovement playerMovent = PlayerMovementFactory.create(world,
				world.getPlayer1(), collisionDetection);
		TouchService touchService = new TouchService(world, playerMovent);
		gameView.addOnSizeListener(touchService);
		return touchService;
	}
}
