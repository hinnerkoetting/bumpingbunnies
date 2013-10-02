package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyMovementStep;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class BunnyMovementStepFactory {

	public static BunnyMovementStep create(List<Player> players, BunnyKillChecker killChecker,
			PlayerMovementCalculationFactory factory) {
		BunnyMovementStep step = new BunnyMovementStep(killChecker, factory);
		for (Player p : players) {
			step.newPlayerJoined(p);
		}
		return step;
	}
}
