package de.oetting.bumpingbunnies.core.networking.factory;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.NoopRemoteSender;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.OnThreadErrorCallback;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;

public class NullOpponentTypeSendFactory implements OpponentTypeSendFactory {

	@Override
	public NetworkSender createNetworkSender(OnThreadErrorCallback stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		return new NoopRemoteSender(socket.getOwner());
	}

	@Override
	public NetworkSender createFastNetworkSender(OnThreadErrorCallback stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		return new NoopRemoteSender(socket.getOwner());
	}

}
