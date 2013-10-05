package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

/**
 * Allows sending messages to a client.
 * 
 */
public interface ThreadedNetworkSender extends NetworkSender {

	void cancel();

	void sendMessage(MessageId id, Object message);

	void sendMessageFast(MessageId id, Object message);

	@Override
	void sendMessage(JsonWrapper wrapper);

	boolean usesThisSocket(MySocket socket);

	void start();

	boolean isConnectionToPlayer(Opponent opponent);
}