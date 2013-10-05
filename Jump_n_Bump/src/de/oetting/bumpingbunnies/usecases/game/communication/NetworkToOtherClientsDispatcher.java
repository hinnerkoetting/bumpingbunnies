package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkSendControl;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

/**
 * Dispatches all incoming traffic to each other client. This should only done by the server. Afterwards the message gets dispatched to game controls.
 * 
 */
public class NetworkToOtherClientsDispatcher implements
		IncomingNetworkDispatcher {

	private final NetworkToGameDispatcher gameDispatcher;
	private final MySocket incomingSocket;
	private final NetworkSendControl sendControl;

	public NetworkToOtherClientsDispatcher(
			MySocket incomingSocket, NetworkToGameDispatcher gameDispatcher, NetworkSendControl sendControl) {
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
}
