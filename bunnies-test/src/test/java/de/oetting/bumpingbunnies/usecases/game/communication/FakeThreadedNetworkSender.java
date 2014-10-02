package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.model.networking.JsonWrapper;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class FakeThreadedNetworkSender implements NetworkSender {

	private final MySocket socket;

	public FakeThreadedNetworkSender(MySocket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void sendMessage(MessageId id, Object message) {
	}

	@Override
	public void sendMessageFast(MessageId id, Object message) {
	}

	@Override
	public void sendMessage(JsonWrapper wrapper) {
	}

	@Override
	public boolean usesThisSocket(MySocket socket) {
		return socket.equals(this.socket);
	}

	@Override
	public boolean isConnectionToPlayer(Opponent opponent) {
		return false;
	}

	@Override
	public void cancel() {
	}

}
