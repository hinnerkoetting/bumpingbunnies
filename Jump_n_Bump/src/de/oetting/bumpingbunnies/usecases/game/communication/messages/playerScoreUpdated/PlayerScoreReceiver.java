package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated;

import java.util.List;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerSearcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerScoreReceiver extends MessageReceiverTemplate<PlayerScoreMessage> {

	private final List<Player> allPlayers;

	public PlayerScoreReceiver(NetworkToGameDispatcher dispatcher, List<Player> allPlayers) {
		super(dispatcher, new PlayerScoreUpdatedMetadata());
		this.allPlayers = allPlayers;
	}

	@Override
	public void onReceiveMessage(PlayerScoreMessage object) {
		Player player = PlayerSearcher.findPlayer(this.allPlayers, object.getPlayerId());
		player.setScore(object.getNewScore());
	}

}
