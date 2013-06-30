package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.communication.InformationSupplier;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public abstract class AbstractInputServiceFactory {

	private static final MyLog LOGGER = Logger
			.getLogger(AbstractInputServiceFactory.class);

	public abstract InputService create(InformationSupplier reicerThread,
			PlayerMovementController movementController, World world);

}
