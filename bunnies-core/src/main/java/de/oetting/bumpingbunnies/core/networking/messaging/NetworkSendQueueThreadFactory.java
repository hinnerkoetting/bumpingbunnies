package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;

public class NetworkSendQueueThreadFactory {

	public static NetworkSendQueueThread create(MySocket socket, GameStopper activity) {
		NetworkSendQueueThread thread = new NetworkSendQueueThread(socket, MessageParserFactory.create(), activity);
		return thread;
	}

}
