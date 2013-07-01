package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.InformationSupplier;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;

public class NetworkReceiverDispatcherThreadFactory {

	public static InformationSupplier create(MySocket socket) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), NetworkConstants.ENCODING));
			NetworkToGameDispatcher networkDispatcher = new NetworkToGameDispatcher();
			NetworkReceiveThread thread = new NetworkReceiveThread(
					reader, new Gson(), networkDispatcher);
			return thread;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
