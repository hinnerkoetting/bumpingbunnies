package de.oetting.bumpingbunnies.core.networking.messaging.playerIsRevived;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.World;

public class PlayerIsRevivedReceiver extends MessageReceiverTemplate<Integer> {
	private final World world;

	public PlayerIsRevivedReceiver(NetworkToGameDispatcher dispatcher, World world) {
		super(dispatcher, new PlayerIsRevivedMetadata());
		this.world = world;
	}

	@Override
	public void onReceiveMessage(Integer playerId) {
		Bunny player = this.world.findBunny(playerId);
		player.setDead(false);
	}

}
