package de.jumpnbump.usecases.game.factories.ai;

import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.android.input.ai.AiInputService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.factories.AbstractInputServiceFactory;
import de.jumpnbump.usecases.game.model.World;

public class NormalAiInputFactory extends AbstractInputServiceFactory {

	@Override
	public InputService create(InformationSupplier reicerThread,
			PlayerMovementController movementController, World world) {
		return new AiInputService(movementController, world);
	}

}
