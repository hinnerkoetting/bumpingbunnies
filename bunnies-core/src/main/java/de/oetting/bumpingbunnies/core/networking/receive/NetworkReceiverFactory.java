package de.oetting.bumpingbunnies.core.networking.receive;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface NetworkReceiverFactory {

	List<NetworkReceiver> create(Player player);

}
