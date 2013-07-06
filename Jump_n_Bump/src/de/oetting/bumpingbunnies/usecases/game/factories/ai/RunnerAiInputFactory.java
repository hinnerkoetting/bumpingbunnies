package de.oetting.bumpingbunnies.usecases.game.factories.ai;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.PathFinder.PathFinderFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.ai.RunnerAiInputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractInputServiceFactory;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class RunnerAiInputFactory extends AbstractInputServiceFactory {

	@Override
	public InputService create(PlayerMovementController movementController,
			World world) {
		return new RunnerAiInputService(world, movementController,
				PathFinderFactory.createPathFinder(movementController
						.getPlayer()));
	}

}
