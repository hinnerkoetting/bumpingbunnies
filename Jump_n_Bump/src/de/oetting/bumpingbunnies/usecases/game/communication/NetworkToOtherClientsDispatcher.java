package de.oetting.bumpingbunnies.usecases.game.communication;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;
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

	public NetworkToOtherClientsDispatcher(List<RemoteSender> sendQueues,
			MySocket incomingSocket, NetworkToGameDispatcher gameDispatcher) {
		super();
		this.sendQueues = sendQueues;
		this.incomingSocket = incomingSocket;
		this.gameDispatcher = gameDispatcher;
	}

	@Override
	public void dispatchPlayerState(PlayerState playerState) {
		for (RemoteSender queue : this.sendQueues) {
			if (!queue.usesThisSocket(this.incomingSocket)) {
				queue.sendPlayerCoordinates(playerState);
			}
		}
		this.gameDispatcher.dispatchPlayerState(playerState);
	}

	@Override
	public NetworkToGameDispatcher getNetworkToGameDispatcher() {
		return this.gameDispatcher;
	}
}
