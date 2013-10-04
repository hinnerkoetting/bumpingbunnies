package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.model.World;

/**
 * Use NewOtherPlayerInputServiceFactory
 * 
 */
@Deprecated
public interface OtherPlayerInputServiceFactory {

	OtherPlayerInputService create(PlayerMovement movementController, World world);

}
