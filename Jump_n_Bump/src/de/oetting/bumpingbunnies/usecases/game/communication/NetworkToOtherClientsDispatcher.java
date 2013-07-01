package de.oetting.bumpingbunnies.usecases.game.communication;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

/**
 * Dispatches all incoming traffic to each other client. Afterwards it is
 * dispatched to game controls.
 * 
 */
public class NetworkToOtherClientsDispatcher implements
		IncomingNetworkDispatcher {

	private final List<RemoteSender> sendQueues;
	private final NetworkToGameDispatcher gameDispatcher;

	public NetworkToOtherClientsDispatcher(List<RemoteSender> sendQueues,
			NetworkToGameDispatcher gameDispatcher) {
		super();
		this.sendQueues = sendQueues;
		this.gameDispatcher = gameDispatcher;
	}

	@Override
	public void dispatchPlayerState(PlayerState playerState) {
		for (RemoteSender queue : this.sendQueues) {
			queue.sendPlayerCoordinates(playerState);
		}
		this.gameDispatcher.dispatchPlayerState(playerState);
	}

	@Override
	public NetworkToGameDispatcher getNetworkToGameDispatcher() {
		return this.gameDispatcher;
	}
}
