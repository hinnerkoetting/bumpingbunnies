package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerSearcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class PlayerIsDeadReceiver extends MessageReceiverTemplate<PlayerIsDead> {

	private final World world;

	public PlayerIsDeadReceiver(NetworkToGameDispatcher dispatcher, World world) {
		super(dispatcher, new PlayerIsDeadMetaData());
		this.world = world;
	}

	@Override
	public void onReceiveMessage(PlayerIsDead object) {
		Player p = findPlayer(object);
		p.setDead(true);
	}

	private Player findPlayer(PlayerIsDead message) {
		return PlayerSearcher.findPlayer(this.world.getAllPlayer(), message.getIdOfDeadPlayer());
	}
}
