package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.usecases.game.communication.IncomingNetworkDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageParser;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToOtherClientsDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;

public class NetworkReceiverDispatcherThreadFactory {

	public static NetworkReceiveThread createGameNetworkReceiver(
			MySocket socket, List<RemoteSender> allRemoteSender,
			NetworkToGameDispatcher networkDispatcher) {

		// always create other clients dispatcher. for clients this will not
		// dispatch incoming events to other sockets
		NetworkToOtherClientsDispatcher otherClientsDispatcher = new NetworkToOtherClientsDispatcher(
				allRemoteSender, socket, networkDispatcher, new MessageParser(
						new Gson()));
		return createNetworkReceiver(socket, otherClientsDispatcher);
	}

	public static NetworkReceiver createRoomNetworkReceiver(MySocket socket) {
		NetworkToGameDispatcher networkDispatcher = new NetworkToGameDispatcher();
		return createNetworkReceiver(socket, networkDispatcher);
	}

	private static MessageParser createParser() {
		return new MessageParser(new Gson());
	}

	private static NetworkReceiveThread createNetworkReceiver(MySocket socket,
			IncomingNetworkDispatcher networkDispatcher) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), NetworkConstants.ENCODING));

			// always create other clients dispatcher. for clients this will not
			// dispatch incoming events to other sockets
			NetworkReceiveThread thread = new NetworkReceiveThread(reader,
					new Gson(), networkDispatcher);
			return thread;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
