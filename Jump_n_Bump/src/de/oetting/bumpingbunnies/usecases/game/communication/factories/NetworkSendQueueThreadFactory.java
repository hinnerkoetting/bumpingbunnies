package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.communication.DummyRemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;

public class NetworkSendQueueThreadFactory {

	public static NetworkSendQueueThread create(MySocket socket, GameActivity activity) {
		NetworkSendQueueThread thread = new NetworkSendQueueThread(socket,
				MessageParserFactory.create(), activity);
		return thread;
	}

	public static RemoteSender createDummyRemoteSender() {
		return new DummyRemoteSender();
	}
}
