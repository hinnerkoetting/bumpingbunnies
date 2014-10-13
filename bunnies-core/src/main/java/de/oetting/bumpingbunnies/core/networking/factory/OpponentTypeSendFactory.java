package de.oetting.bumpingbunnies.core.networking.factory;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.OnThreadErrorCallback;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;

public interface OpponentTypeSendFactory {

	NetworkSender createNetworkSender(OnThreadErrorCallback GameStopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback);

	NetworkSender createFastNetworkSender(OnThreadErrorCallback stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback);
}
