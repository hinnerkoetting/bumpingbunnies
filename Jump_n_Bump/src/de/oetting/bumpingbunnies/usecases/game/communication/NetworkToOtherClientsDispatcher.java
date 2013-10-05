package de.oetting.bumpingbunnies.usecases.game.communication;

import java.util.List;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

/**
 * Dispatches all incoming traffic to each other client. This should only done by the server. Afterwards the message gets dispatched to game controls.
 * 
 */
public class NetworkToOtherClientsDispatcher implements
		IncomingNetworkDispatcher {

	private final List<? extends ThreadedNetworkSender> sendQueues;
	private final NetworkToGameDispatcher gameDispatcher;
	private final MySocket incomingSocket;

	public NetworkToOtherClientsDispatcher(List<? extends ThreadedNetworkSender> sendQueues,
			MySocket incomingSocket, NetworkToGameDispatcher gameDispatcher) {
		super();
		this.sendQueues = sendQueues;
		this.incomingSocket = incomingSocket;
		this.gameDispatcher = gameDispatcher;
	}

	@Override
	public void dispatchPlayerState(JsonWrapper wrapper) {
		for (ThreadedNetworkSender queue : this.sendQueues) {
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
