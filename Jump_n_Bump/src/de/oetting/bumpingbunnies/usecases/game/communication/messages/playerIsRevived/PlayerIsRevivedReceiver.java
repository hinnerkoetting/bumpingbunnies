package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived;

import java.util.List;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerSearcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerIsRevivedReceiver extends MessageReceiverTemplate<Integer> {
	private final List<Player> allPlayers;

	public PlayerIsRevivedReceiver(NetworkToGameDispatcher dispatcher, List<Player> allPlayers) {
		super(dispatcher, new PlayerIsRevivedMetadata());
		this.allPlayers = allPlayers;
	}

	@Override
	public void onReceiveMessage(Integer object) {
		Player player = PlayerSearcher.findPlayer(this.allPlayers, object);
		player.setDead(false);
	}

}
