package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.model.networking.JsonWrapper;

public interface IncomingNetworkDispatcher {

	void dispatchMessage(JsonWrapper wrapper);

	NetworkToGameDispatcher getNetworkToGameDispatcher();

}
