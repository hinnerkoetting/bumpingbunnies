package de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerSearcher;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.ResetToScorePoint;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class SpawnPointReceiver extends MessageReceiverTemplate<SpawnPointMessage> {

	private final World world;

	public SpawnPointReceiver(NetworkToGameDispatcher dispatcher, World world) {
		super(dispatcher, new SpawnPointMetadata());
		this.world = world;
	}

	@Override
	public void onReceiveMessage(SpawnPointMessage object) {
		Player p = findPlayer(object);

		ResetToScorePoint.resetPlayerToSpawnPoint(object.getSpawnPoint(), p);
	}

	private Player findPlayer(SpawnPointMessage message) {
		return PlayerSearcher.findPlayer(this.world.getAllPlayer(), message.getPlayerId());
	}
}
