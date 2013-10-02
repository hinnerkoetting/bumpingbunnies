package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public interface OtherPlayerInputServiceFactory {

	OtherPlayerInputService create(PlayerMovementController movementController, World world);

}
