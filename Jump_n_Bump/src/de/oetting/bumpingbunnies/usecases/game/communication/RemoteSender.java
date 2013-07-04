package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;

public interface RemoteSender {

	void sendPlayerCoordinates(Player player);

	void cancel();

	void sendPlayerCoordinates(PlayerState playerState);

	void sendMessage(int id, Object message);

	void sendMessage(JsonWrapper wrapper);

	boolean usesThisSocket(MySocket socket);
}