package de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint;

import de.oetting.bumpingbunnies.core.game.spawnpoint.ResetToScorePoint;
import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Player;

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
		return this.world.findPlayer(message.getPlayerId());
	}
}
