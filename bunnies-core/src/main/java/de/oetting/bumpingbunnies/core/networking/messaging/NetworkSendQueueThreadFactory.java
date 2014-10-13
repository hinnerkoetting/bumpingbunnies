package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.OnThreadErrorCallback;

public class NetworkSendQueueThreadFactory {

	public static NetworkSendQueueThread create(MySocket socket, OnThreadErrorCallback activity) {
		NetworkSendQueueThread thread = new NetworkSendQueueThread(socket, MessageParserFactory.create(), activity);
		return thread;
	}

}
