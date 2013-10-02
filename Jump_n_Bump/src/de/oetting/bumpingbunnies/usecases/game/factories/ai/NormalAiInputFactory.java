package de.oetting.bumpingbunnies.usecases.game.factories.ai;

import de.oetting.bumpingbunnies.usecases.game.android.input.ai.AiInputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.factories.OtherPlayerInputServiceFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.OtherPlayerInputService;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class NormalAiInputFactory implements OtherPlayerInputServiceFactory {

	@Override
	public OtherPlayerInputService create(PlayerMovement movementController,
			World world) {
		return new AiInputService(movementController, world);
	}

}
