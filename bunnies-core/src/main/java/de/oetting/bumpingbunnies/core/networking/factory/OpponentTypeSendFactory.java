package de.oetting.bumpingbunnies.core.networking.factory;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public interface OpponentTypeSendFactory {

	NetworkSender createNetworkSender(ThreadErrorCallback GameStopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback);

	NetworkSender createFastNetworkSender(ThreadErrorCallback stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback);
}
