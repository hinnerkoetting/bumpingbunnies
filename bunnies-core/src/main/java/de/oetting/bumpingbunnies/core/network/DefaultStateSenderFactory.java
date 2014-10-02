package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.networking.sender.GameNetworkSender;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class DefaultStateSenderFactory implements StateSenderFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultStateSenderFactory.class);
	private final NetworkMessageDistributor sendControl;
	private final Player myPlayer;

	public DefaultStateSenderFactory(NetworkMessageDistributor sendControl, Player myPlayer) {
		super();
		this.sendControl = sendControl;
		this.myPlayer = myPlayer;
	}

	@Override
	public StateSender create(Player p) {
		if (p.getOpponent().isLocalPlayer()) {
			LOGGER.info("Found local player. DummyStateSender...");
			return new DummyStateSender(p);
		} else {
			LOGGER.info("Found network player. Creating gamenetworksender...");
			return new GameNetworkSender(this.myPlayer, this.sendControl.findConnection(p.getOpponent()));
		}
	}

}
