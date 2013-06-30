package de.oetting.bumpingbunnies.usecases.game.factories.ai;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.ai.DummyInputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.communication.InformationSupplier;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractInputServiceFactory;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class NoAiInputFactory extends AbstractInputServiceFactory {

	@Override
	public InputService create(InformationSupplier reicerThread,
			PlayerMovementController movementController, World world) {
		return new DummyInputService();
	}

}
