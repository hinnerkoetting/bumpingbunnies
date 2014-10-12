package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactory;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactoryFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;

public class RemoteConnectionFactory {

	private final GameStopper stopper;
	private final PlayerDisconnectedCallback disconnectCallback;

	public RemoteConnectionFactory(GameStopper stopper, PlayerDisconnectedCallback disconnectCallback) {
		this.stopper = stopper;
		this.disconnectCallback = disconnectCallback;
	}

	public NetworkSender create(MySocket socket) {
		OpponentTypeSendFactory sendFactory = new OpponentTypeSendFactoryFactory().createSendFactory(socket.getOwner().getType());
		return sendFactory.createNetworkSender(this.stopper, socket, disconnectCallback);
	}

	public NetworkSender createFastSender(MySocket socket) {
		OpponentTypeSendFactory sendFactory = new OpponentTypeSendFactoryFactory().createSendFactory(socket.getOwner().getType());
		return sendFactory.createFastNetworkSender(this.stopper, socket, disconnectCallback);
	}

}
