package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public interface RemoteSender {

	void sendPlayerCoordinates(Player player);

	void cancel();

	void sendPlayerCoordinates(PlayerState playerState);
}