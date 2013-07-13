package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead;

import java.util.List;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.ResetToScorePoint;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerIsDeadReceiver extends MessageReceiverTemplate<PlayerIsDead> {

	private final List<Player> allPlayers;

	public PlayerIsDeadReceiver(NetworkToGameDispatcher dispatcher, List<Player> allPlayers) {
		super(dispatcher, MessageId.PLAYER_IS_DEAD_MESSAGE, PlayerIsDead.class);
		this.allPlayers = allPlayers;
	}

	@Override
	public void onReceiveMessage(PlayerIsDead object) {
		Player p = findPlayer(object);
		p.setDead(true);
		ResetToScorePoint.resetPlayerToSpawnPoint(object.getNextSpawnPoint(), p);
	}

	private Player findPlayer(PlayerIsDead message) {
		for (Player p : this.allPlayers) {
			if (p.id() == message.getIdOfDeadPlayer()) {
				return p;
			}
		}
		throw new IllegalStateException("Could not find player with id " + message.getIdOfDeadPlayer());
	}

}
