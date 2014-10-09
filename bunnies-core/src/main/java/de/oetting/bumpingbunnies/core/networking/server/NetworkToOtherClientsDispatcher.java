package de.oetting.bumpingbunnies.core.networking.server;

import de.oetting.bumpingbunnies.core.network.IncomingNetworkDispatcher;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;

/**
 * Dispatches all incoming traffic to each other client. This should only done
 * by the server. Afterwards the message gets dispatched to game controls.
 * 
 */
public class NetworkToOtherClientsDispatcher implements IncomingNetworkDispatcher {

	private final NetworkToGameDispatcher gameDispatcher;
	private final MySocket incomingSocket;
	private final NetworkMessageDistributor sendControl;

	public NetworkToOtherClientsDispatcher(MySocket incomingSocket, NetworkToGameDispatcher gameDispatcher, NetworkMessageDistributor sendControl) {
		super();
		this.incomingSocket = incomingSocket;
		this.gameDispatcher = gameDispatcher;
		this.sendControl = sendControl;
	}

	@Override
	public void dispatchMessage(JsonWrapper wrapper) {
		this.sendControl.sendMessageExceptToOneSocket(wrapper, this.incomingSocket);
		this.gameDispatcher.dispatchMessage(wrapper);
	}

	@Override
	public NetworkToGameDispatcher getNetworkToGameDispatcher() {
		return this.gameDispatcher;
	}

	@Override
	public void playerWasDisconnected(Opponent owner) {
		JsonWrapper wrapper = JsonWrapper.create(MessageId.PLAYER_DISCONNECTED, MessageParserFactory.create().encodeMessage(owner));
		sendControl.sendMessageExceptToOneSocket(wrapper, incomingSocket);
		gameDispatcher.playerWasDisconnected(owner);
	}
}
