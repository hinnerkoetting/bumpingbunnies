package de.jumpnbump.usecases.game.communication.factories;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.NetworkConstants;
import de.jumpnbump.usecases.game.communication.NetworkReceiveDispatcherThread;
import de.jumpnbump.usecases.start.communication.MySocket;

public class NetworkReceiverDispatcherThreadFactory {

	public static InformationSupplier create(MySocket socket) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), NetworkConstants.ENCODING));
			NetworkReceiveDispatcherThread thread = new NetworkReceiveDispatcherThread(
					reader, new Gson());
			return thread;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
