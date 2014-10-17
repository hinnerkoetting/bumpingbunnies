package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class NetworkSendQueueThreadFactory {

	public static NetworkSendQueueThread create(MySocket socket, ThreadErrorCallback threadErrorCallback, PlayerDisconnectedCallback disconnectCallback) {
		NetworkSendQueueThread thread = new NetworkSendQueueThread(socket, MessageParserFactory.create(), threadErrorCallback, disconnectCallback);
		return thread;
	}

}
