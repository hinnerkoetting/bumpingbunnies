package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public abstract class AbstractInputServiceFactory {

	public abstract InputService create(
			PlayerMovementController movementController, World world);

}
