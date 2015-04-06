package de.oetting.bumpingbunnies.core.game.steps.factory;

import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.steps.BunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.BunnyMovementStep;
import de.oetting.bumpingbunnies.core.game.steps.FixPlayerPosition;
import de.oetting.bumpingbunnies.core.world.ObjectProvider;

public class BunnyMovementStepFactory {

	public static BunnyMovementStep create(BunnyKillChecker killChecker,
			PlayerMovementCalculationFactory factory, ObjectProvider objectProvier) {
		BunnyMovementStep step = new BunnyMovementStep(killChecker, factory, new FixPlayerPosition(new CollisionDetection(objectProvier)));
		return step;
	}
}
