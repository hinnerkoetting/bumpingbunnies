package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public interface IncomingNetworkDispatcher {

	void dispatchPlayerState(PlayerState playerState);

	NetworkToGameDispatcher getNetworkToGameDispatcher();
}
