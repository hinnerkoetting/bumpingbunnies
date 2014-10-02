package de.oetting.bumpingbunnies.core.networking.messaging.playerIsRevived;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerIsRevivedReceiver extends MessageReceiverTemplate<Integer> {
	private final World world;

	public PlayerIsRevivedReceiver(NetworkToGameDispatcher dispatcher, World world) {
		super(dispatcher, new PlayerIsRevivedMetadata());
		this.world = world;
	}

	@Override
	public void onReceiveMessage(Integer playerId) {
		Player player = this.world.findPlayer(playerId);
		player.setDead(false);
	}

}
