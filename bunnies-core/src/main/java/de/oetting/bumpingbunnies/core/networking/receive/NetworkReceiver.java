package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public interface NetworkReceiver {

	/**
	 * Stops the thread but does not close any sockets.
	 */
	void cancel();

	/**
	 * Stops the thread and all sockets
	 */
	void shutdown();

	void start();

	NetworkToGameDispatcher getGameDispatcher();

	boolean belongsToPlayer(Player p);

	boolean belongsToSocket(MySocket socket);

}