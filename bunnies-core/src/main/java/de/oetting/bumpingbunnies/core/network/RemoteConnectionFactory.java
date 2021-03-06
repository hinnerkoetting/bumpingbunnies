package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactory;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactoryFactory;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class RemoteConnectionFactory {

	private final ThreadErrorCallback stopper;
	private PlayerDisconnectedCallback disconnectCallback;

	public RemoteConnectionFactory(ThreadErrorCallback stopper) {
		this.stopper = stopper;
	}

	public NetworkSender create(MySocket socket) {
		OpponentTypeSendFactory sendFactory = new OpponentTypeSendFactoryFactory().createSendFactory(socket.getConnectionIdentifier().getType());
		return sendFactory.createNetworkSender(this.stopper, socket, disconnectCallback);
	}

	public NetworkSender createFastSender(MySocket socket) {
		OpponentTypeSendFactory sendFactory = new OpponentTypeSendFactoryFactory().createSendFactory(socket.getConnectionIdentifier().getType());
		return sendFactory.createFastNetworkSender(this.stopper, socket, disconnectCallback);
	}

	public void setDisconnectCallback(PlayerDisconnectedCallback disconnectCallback) {
		this.disconnectCallback = disconnectCallback;
	}

}
