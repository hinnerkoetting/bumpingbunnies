package de.jumpnbump.usecases.game.communication.factories;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import android.bluetooth.BluetoothSocket;

import com.google.gson.Gson;

import de.jumpnbump.usecases.game.communication.NetworkConstants;
import de.jumpnbump.usecases.game.communication.NetworkSendQueueThread;
import de.jumpnbump.usecases.game.communication.StateSender;

public class StateSenderFactory {

	// todo rework?
	private static StateSender senderSingleton;

	public static StateSender createNetworkSender(BluetoothSocket socket) {
		if (senderSingleton == null) {
			senderSingleton = create(socket);
		}
		return senderSingleton;
		// GameNetworkSendThread thread = new GameNetworkSendThread(socket);
		// thread.start();
		// return thread;
		// if (senderSingleton == null) {
		// senderSingleton = create(socket);
		// }
		// return senderSingleton;
	}

	private static StateSender create(BluetoothSocket socket) {
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
}
