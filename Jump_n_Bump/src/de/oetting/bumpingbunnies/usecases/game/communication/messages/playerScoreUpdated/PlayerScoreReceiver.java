package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

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
