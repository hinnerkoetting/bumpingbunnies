package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;

public interface IncomingNetworkDispatcher {

	void dispatchMessage(JsonWrapper wrapper);

	NetworkToGameDispatcher getNetworkToGameDispatcher();

	void playerWasDisconnected(Opponent owner);

}
