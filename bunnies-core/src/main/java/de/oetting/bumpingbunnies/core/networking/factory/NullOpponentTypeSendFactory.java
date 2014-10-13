package de.oetting.bumpingbunnies.core.networking.factory;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.NoopRemoteSender;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class NullOpponentTypeSendFactory implements OpponentTypeSendFactory {

	@Override
	public NetworkSender createNetworkSender(ThreadErrorCallback stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		return new NoopRemoteSender(socket.getOwner());
	}

	@Override
	public NetworkSender createFastNetworkSender(ThreadErrorCallback stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		return new NoopRemoteSender(socket.getOwner());
	}

}
