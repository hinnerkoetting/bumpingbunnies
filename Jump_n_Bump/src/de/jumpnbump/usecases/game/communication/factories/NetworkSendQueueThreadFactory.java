package de.jumpnbump.usecases.game.communication.factories;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import android.bluetooth.BluetoothSocket;

import com.google.gson.Gson;

import de.jumpnbump.usecases.game.communication.DummyRemoteSender;
import de.jumpnbump.usecases.game.communication.NetworkConstants;
import de.jumpnbump.usecases.game.communication.NetworkSendQueueThread;
import de.jumpnbump.usecases.game.communication.RemoteSender;

public class NetworkSendQueueThreadFactory {

	public static NetworkSendQueueThread create(BluetoothSocket socket) {
		try {
			Writer writer = new OutputStreamWriter(socket.getOutputStream(),
					NetworkConstants.ENCODING);
			NetworkSendQueueThread thread = new NetworkSendQueueThread(writer,
					new Gson());
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
