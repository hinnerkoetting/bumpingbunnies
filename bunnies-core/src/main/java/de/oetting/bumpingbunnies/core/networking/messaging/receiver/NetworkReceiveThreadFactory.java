package de.oetting.bumpingbunnies.core.networking.messaging.receiver;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveThread;

public class NetworkReceiveThreadFactory {

	public static NetworkReceiveThread create(MySocket socket,
			NetworkToGameDispatcher networkDispatcher) {
		Gson gson = new Gson();
		return new NetworkReceiveThread(gson, networkDispatcher, socket);
	}
}
