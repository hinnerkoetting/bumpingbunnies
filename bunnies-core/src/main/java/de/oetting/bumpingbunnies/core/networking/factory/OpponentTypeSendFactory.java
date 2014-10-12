package de.oetting.bumpingbunnies.core.networking.factory;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;

public interface OpponentTypeSendFactory {

	NetworkSender createNetworkSender(GameStopper GameStopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback);

	NetworkSender createFastNetworkSender(GameStopper stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback);
}
