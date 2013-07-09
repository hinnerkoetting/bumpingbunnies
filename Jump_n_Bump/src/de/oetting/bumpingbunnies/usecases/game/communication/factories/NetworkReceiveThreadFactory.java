package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;

public class NetworkReceiveThreadFactory {

	public static NetworkReceiveThread create(MySocket socket,
			NetworkToGameDispatcher networkDispatcher) {
		Gson gson = new Gson();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), NetworkConstants.ENCODING));

			return new NetworkReceiveThread(reader, gson, networkDispatcher);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
