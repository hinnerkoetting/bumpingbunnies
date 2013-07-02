package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

public interface IncomingNetworkDispatcher {

	void dispatchPlayerState(JsonWrapper wrapper);

	NetworkToGameDispatcher getNetworkToGameDispatcher();

}
