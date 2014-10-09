package de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class PlayerIsDeadReceiver extends MessageReceiverTemplate<PlayerIsDeadMessage> {

	private final World world;

	public PlayerIsDeadReceiver(NetworkToGameDispatcher dispatcher, World world) {
		super(dispatcher, new PlayerIsDeadMetaData());
		this.world = world;
	}

	@Override
	public void onReceiveMessage(PlayerIsDeadMessage object) {
		Player p = findPlayer(object);
		p.setDead(true);
	}

	private Player findPlayer(PlayerIsDeadMessage message) {
		return this.world.findPlayer(message.getPlayerId());
	}
}
