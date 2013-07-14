package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead;

import java.util.List;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerSearcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerIsDeadReceiver extends MessageReceiverTemplate<PlayerIsDead> {

	private final List<Player> allPlayers;

	public PlayerIsDeadReceiver(NetworkToGameDispatcher dispatcher, List<Player> allPlayers) {
		super(dispatcher, new PlayerIsDeadMetaData());
		this.allPlayers = allPlayers;
	}

	@Override
	public void onReceiveMessage(PlayerIsDead object) {
		Player p = findPlayer(object);
		p.setDead(true);
	}

	private Player findPlayer(PlayerIsDead message) {
		return PlayerSearcher.findPlayer(this.allPlayers, message.getIdOfDeadPlayer());
	}
}
