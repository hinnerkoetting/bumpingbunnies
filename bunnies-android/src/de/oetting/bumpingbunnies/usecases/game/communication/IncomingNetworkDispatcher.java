package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

public interface IncomingNetworkDispatcher {

	void dispatchMessage(JsonWrapper wrapper);

	NetworkToGameDispatcher getNetworkToGameDispatcher();

}
