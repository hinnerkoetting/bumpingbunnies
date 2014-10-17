package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class NoopRemoteSender implements NetworkSender {

	private final ConnectionIdentifier opponent;

	public NoopRemoteSender(ConnectionIdentifier opponent) {
		this.opponent = opponent;
	}

	@Override
	public boolean usesThisSocket(MySocket socket) {
		return false;
	}

	@Override
	public void sendMessage(MessageId id, Object message) {
	}

	@Override
	public void sendMessage(JsonWrapper wrapper) {
	}

	@Override
	public boolean isConnectionToPlayer(ConnectionIdentifier opponent) {
		return opponent.equals(this.opponent);
	}

	@Override
	public void cancel() {
	}

	@Override
	public void start() {
	}

}
