package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.NetworkSendQueueThreadFactory;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class BluetoothOpponentTypeSendFactory implements OpponentTypeSendFactory {

	@Override
	public NetworkSender createNetworkSender(ThreadErrorCallback stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		return NetworkSendQueueThreadFactory.create(socket, stopper);
	}

	@Override
	public NetworkSender createFastNetworkSender(ThreadErrorCallback stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		return SimpleNetworkSenderFactory.createNetworkSender(socket, disconnectCallback);
	}
}
