package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

/**
 * Allows sending messages to a client.
 * 
 */
public interface RemoteSender extends NetworkSender {

	void sendPlayerCoordinates(Player player);

	void cancel();

	void sendPlayerCoordinates(PlayerState playerState);

	void sendMessage(int id, Object message);

	@Override
	void sendMessage(JsonWrapper wrapper);

	boolean usesThisSocket(MySocket socket);

	void start();
}