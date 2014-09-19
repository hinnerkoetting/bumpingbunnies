package de.oetting.bumpingbunnies.communication.messageInterface;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public interface NetworkSender {

	void sendMessage(JsonWrapper message);

	void sendMessage(MessageId id, Object message);

	void sendMessageFast(MessageId id, Object message);

	boolean usesThisSocket(MySocket socket);

	boolean isConnectionToPlayer(Opponent opponent);

	void cancel();
}
