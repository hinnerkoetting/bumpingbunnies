package de.oetting.bumpingbunnies.usecases.game.communication;

import java.util.List;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

/**
 * Dispatches all incoming traffic to each other client. Afterwards it is dispatched to game controls.
 * 
 */
public class NetworkToOtherClientsDispatcher implements
		IncomingNetworkDispatcher {

	private final List<? extends RemoteSender> sendQueues;
	private final NetworkToGameDispatcher gameDispatcher;
	private final MySocket incomingSocket;

	public NetworkToOtherClientsDispatcher(List<? extends RemoteSender> sendQueues,
			MySocket incomingSocket, NetworkToGameDispatcher gameDispatcher) {
		super();
		this.sendQueues = sendQueues;
		this.incomingSocket = incomingSocket;
		this.gameDispatcher = gameDispatcher;
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
