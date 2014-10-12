package de.oetting.bumpingbunnies.core.networking.receive;

import java.util.List;

import de.oetting.bumpingbunnies.core.network.MySocket;

public interface NetworkReceiverFactory {

	List<NetworkReceiver> create(MySocket socket);

}
