package de.oetting.bumpingbunnies.core.networking.factory;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.DummyRemoteSender;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;

public class NullOpponentTypeSendFactory implements OpponentTypeSendFactory {

	@Override
	public NetworkSender createNetworkSender(GameStopper stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		return new DummyRemoteSender(socket.getOwner());
	}

	@Override
	public NetworkSender createFastNetworkSender(GameStopper stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		return new DummyRemoteSender(socket.getOwner());
	}

}
