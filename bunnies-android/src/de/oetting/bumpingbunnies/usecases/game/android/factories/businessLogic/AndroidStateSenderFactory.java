package de.oetting.bumpingbunnies.usecases.game.android.factories.businessLogic;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkSendControl;
import de.oetting.bumpingbunnies.usecases.game.communication.DummyStateSender;
import de.oetting.bumpingbunnies.usecases.game.communication.GameNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.factories.businessLogic.StateSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AndroidStateSenderFactory implements StateSenderFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidStateSenderFactory.class);
	private final NetworkSendControl sendControl;
	private final Player myPlayer;

	public AndroidStateSenderFactory(NetworkSendControl sendControl, Player myPlayer) {
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
