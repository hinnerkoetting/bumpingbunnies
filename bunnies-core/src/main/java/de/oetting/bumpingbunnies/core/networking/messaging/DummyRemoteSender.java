package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class DummyRemoteSender implements NetworkSender {

	private final Opponent opponent;

	public DummyRemoteSender(Opponent opponent) {
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
