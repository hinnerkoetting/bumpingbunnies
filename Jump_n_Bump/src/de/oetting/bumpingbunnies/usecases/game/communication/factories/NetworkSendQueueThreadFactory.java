package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkSendQueueThread;

public class NetworkSendQueueThreadFactory {

	public static NetworkSendQueueThread create(MySocket socket, GameActivity activity) {
		NetworkSendQueueThread thread = new NetworkSendQueueThread(socket,
				MessageParserFactory.create(), activity);
		thread.start();
		return thread;
	}

}
