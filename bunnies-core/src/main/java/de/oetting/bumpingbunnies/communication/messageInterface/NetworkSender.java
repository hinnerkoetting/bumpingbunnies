package de.oetting.bumpingbunnies.communication.messageInterface;

import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.model.networking.JsonWrapper;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public interface NetworkSender {

	void sendMessage(JsonWrapper message);

	void sendMessage(MessageId id, Object message);

	void sendMessageFast(MessageId id, Object message);

	boolean usesThisSocket(MySocket socket);

	boolean isConnectionToPlayer(Opponent opponent);

	void cancel();
}
