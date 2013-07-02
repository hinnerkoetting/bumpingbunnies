package de.oetting.bumpingbunnies.usecases.game.factories.ai;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.ai.AiInputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractInputServiceFactory;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class NormalAiInputFactory extends AbstractInputServiceFactory {

	@Override
	public InputService create(NetworkReceiver reicerThread,
			PlayerMovementController movementController, World world) {
		return new AiInputService(movementController, world);
	}

}
