package de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class PlayerScoreReceiver extends MessageReceiverTemplate<PlayerScoreMessage> {

	private final World world;

	public PlayerScoreReceiver(NetworkToGameDispatcher dispatcher, World world) {
		super(dispatcher, new PlayerScoreUpdatedMetadata());
		this.world = world;
	}

	@Override
	public void onReceiveMessage(PlayerScoreMessage object) {
		Player player = this.world.findPlayer(object.getPlayerId());
		player.setScore(object.getNewScore());
	}

}
