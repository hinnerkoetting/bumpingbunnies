package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.communication.DummyRemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;

public class NetworkSendQueueThreadFactory {

	public static NetworkSendQueueThread create(MySocket socket) {
		try {
			Writer writer = new OutputStreamWriter(socket.getOutputStream(),
					NetworkConstants.ENCODING);
			NetworkSendQueueThread thread = new NetworkSendQueueThread(socket,
					writer, new Gson());
			thread.start();
			return thread;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static RemoteSender createDummyRemoteSender() {
		return new DummyRemoteSender();
	}
}
