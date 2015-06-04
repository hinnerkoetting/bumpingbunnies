package de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.World;

public class PlayerScoreReceiver extends MessageReceiverTemplate<PlayerScoreMessage> {

	private final World world;

	public PlayerScoreReceiver(NetworkToGameDispatcher dispatcher, World world) {
		super(dispatcher, new PlayerScoreUpdatedMetadata());
		this.world = world;
	}

	@Override
	public void onReceiveMessage(PlayerScoreMessage object) {
		Bunny player = this.world.findBunny(object.getPlayerId());
		player.setScore(object.getNewScore());
	}

}
