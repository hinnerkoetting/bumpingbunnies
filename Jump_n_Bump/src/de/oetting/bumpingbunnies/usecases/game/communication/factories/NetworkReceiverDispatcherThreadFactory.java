package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.usecases.game.communication.InformationSupplier;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToOtherClientsDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;

public class NetworkReceiverDispatcherThreadFactory {

	public static InformationSupplier create(MySocket socket,
			List<RemoteSender> allRemoteSender) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), NetworkConstants.ENCODING));
			NetworkToGameDispatcher networkDispatcher = new NetworkToGameDispatcher();
			// always create other clients dispatcher. for clients this will not
			// dispatch incoming events to other sockets
			NetworkToOtherClientsDispatcher otherClientsDispatcher = new NetworkToOtherClientsDispatcher(
					allRemoteSender, socket, networkDispatcher);
			NetworkReceiveThread thread = new NetworkReceiveThread(reader,
					new Gson(), otherClientsDispatcher);
			return thread;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
