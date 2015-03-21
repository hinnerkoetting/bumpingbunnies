package de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class PlayerIsDeadReceiver extends MessageReceiverTemplate<PlayerIsDeadMessage> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerIsDeadReceiver.class);

	private final World world;
	private final MusicPlayer deadPlayerMusic;

	public PlayerIsDeadReceiver(NetworkToGameDispatcher dispatcher, World world, MusicPlayer deadPlayerMusic) {
		super(dispatcher, new PlayerIsDeadMetaData());
		this.world = world;
		this.deadPlayerMusic = deadPlayerMusic;
	}

	@Override
	public void onReceiveMessage(PlayerIsDeadMessage object) {
		if (world.existsPlayer(object.getPlayerId())) {
			Player p = findPlayer(object);
			p.setDead(true);
			deadPlayerMusic.start();
		} else {
			LOGGER.warn("Received player is dead message but player does not exist %d ", object.getPlayerId());
		}
	}

	private Player findPlayer(PlayerIsDeadMessage message) {
		return this.world.findPlayer(message.getPlayerId());
	}
}
