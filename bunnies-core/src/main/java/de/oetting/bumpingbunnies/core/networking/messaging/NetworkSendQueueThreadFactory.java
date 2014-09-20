package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;

public class NetworkSendQueueThreadFactory {

	public static NetworkSendQueueThread create(MySocket socket, GameStopper activity) {
		NetworkSendQueueThread thread = new NetworkSendQueueThread(socket, MessageParserFactory.create(), activity);
		thread.start();
		return thread;
	}

}
