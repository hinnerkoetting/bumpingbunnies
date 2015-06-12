package de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated;

import de.oetting.bumpingbunnies.core.game.steps.ScoreboardSynchronisation;
import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.World;

public class PlayerScoreReceiver extends MessageReceiverTemplate<PlayerScoreMessage> {

	private final World world;
	private final ScoreboardSynchronisation scoreboardSynchronisation;

	public PlayerScoreReceiver(NetworkToGameDispatcher dispatcher, World world, ScoreboardSynchronisation scoreboardSynchronisation) {
		super(dispatcher, new PlayerScoreUpdatedMetadata());
		this.world = world;
		this.scoreboardSynchronisation = scoreboardSynchronisation;
	}

	@Override
	public void onReceiveMessage(PlayerScoreMessage object) {
		Bunny player = this.world.findBunny(object.getPlayerId());
		player.setScore(object.getNewScore());
		scoreboardSynchronisation.scoreIsChanged();
	}

}
