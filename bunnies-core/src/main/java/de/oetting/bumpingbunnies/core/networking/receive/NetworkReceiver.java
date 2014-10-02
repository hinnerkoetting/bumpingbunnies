package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface NetworkReceiver {

	void cancel();

	void start();

	NetworkToGameDispatcher getGameDispatcher();

	boolean belongsToPlayer(Player p);

}