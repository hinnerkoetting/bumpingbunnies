package de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint;

import de.oetting.bumpingbunnies.core.game.spawnpoint.ResetToScorePoint;
import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.world.PlayerDoesNotExist;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class SpawnPointReceiver extends MessageReceiverTemplate<SpawnPointMessage> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpawnPointReceiver.class);

	private final World world;

	public SpawnPointReceiver(NetworkToGameDispatcher dispatcher, World world) {
		super(dispatcher, new SpawnPointMetadata());
		this.world = world;
	}

	@Override
	public void onReceiveMessage(SpawnPointMessage object) {
		int count = 0;
		int maxTries = 5;
		while (count++ < maxTries) {
			try {
				Player p = findPlayer(object);
				ResetToScorePoint.resetPlayerToSpawnPoint(object.getSpawnPoint(), p);
			} catch (PlayerDoesNotExist e) {
				handleException(count, maxTries, e);
			}
		}
	}

	private void handleException(int count, int maxTries, PlayerDoesNotExist e) {
		if (count < maxTries) {
			LOGGER.info("Trying to receive spawnpoint but player was not yet received from the server. Trying again in 100ms");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
			}
		} else {
			throw e;
		}
	}

	private Player findPlayer(SpawnPointMessage message) {
		return this.world.findPlayer(message.getPlayerId());
	}
}
