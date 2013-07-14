package de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint;

import java.util.List;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerSearcher;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.ResetToScorePoint;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class SpawnPointReceiver extends MessageReceiverTemplate<SpawnPointMessage> {

	private final List<Player> allPlayers;

	public SpawnPointReceiver(NetworkToGameDispatcher dispatcher, List<Player> allPlayers) {
		super(dispatcher, new SpawnPointMetadata());
		this.allPlayers = allPlayers;
	}

	@Override
	public void onReceiveMessage(SpawnPointMessage object) {
		Player p = findPlayer(object);

		ResetToScorePoint.resetPlayerToSpawnPoint(object.getSpawnPoint(), p);
	}

	private Player findPlayer(SpawnPointMessage message) {
		return PlayerSearcher.findPlayer(this.allPlayers, message.getPlayerId());
	}
}
