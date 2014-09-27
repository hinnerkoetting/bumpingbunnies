package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.model.networking.JsonWrapper;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class DummyRemoteSender implements NetworkSender {

	private final Opponent opponent;

	public DummyRemoteSender(Opponent opponent) {
		super();
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
	public void sendMessageFast(MessageId id, Object message) {
	}

	@Override
	public boolean isConnectionToPlayer(Opponent opponent) {
		return opponent.equals(this.opponent);
	}

	@Override
	public void cancel() {
	}

}