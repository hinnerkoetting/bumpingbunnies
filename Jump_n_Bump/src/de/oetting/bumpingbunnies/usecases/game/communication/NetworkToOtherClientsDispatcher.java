package de.oetting.bumpingbunnies.usecases.game.communication;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;

/**
 * Dispatches all incoming traffic to each other client. Afterwards it is
 * dispatched to game controls.
 * 
 */
public class NetworkToOtherClientsDispatcher implements
		IncomingNetworkDispatcher {

	private final List<RemoteSender> sendQueues;
	private final NetworkToGameDispatcher gameDispatcher;
	private final MySocket incomingSocket;
	private final MessageParser parser;

	public NetworkToOtherClientsDispatcher(List<RemoteSender> sendQueues,
			MySocket incomingSocket, NetworkToGameDispatcher gameDispatcher,
			MessageParser parser) {
		super();
		this.sendQueues = sendQueues;
		this.incomingSocket = incomingSocket;
		this.gameDispatcher = gameDispatcher;
		this.parser = parser;
	}

	@Override
	public void dispatchPlayerState(JsonWrapper wrapper) {
		for (RemoteSender queue : this.sendQueues) {
			if (!queue.usesThisSocket(this.incomingSocket)) {
				queue.sendMessage(wrapper);
			}
		}
		this.gameDispatcher.dispatchPlayerState(wrapper);
	}

	@Override
	public NetworkToGameDispatcher getNetworkToGameDispatcher() {
		return this.gameDispatcher;
	}
}
