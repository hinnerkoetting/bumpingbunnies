package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;

public class NetworkReceiveThreadFactory {

	public static NetworkReceiveThread create(MySocket socket,
			NetworkToGameDispatcher networkDispatcher) {
		Gson gson = new Gson();
		return new NetworkReceiveThread(gson, networkDispatcher, socket);
	}
}
