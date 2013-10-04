package de.oetting.bumpingbunnies.usecases.game.android.factories.businessLogic;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.communication.DummyStateSender;
import de.oetting.bumpingbunnies.usecases.game.communication.GameNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.factories.businessLogic.StateSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AndroidStateSenderFactory implements StateSenderFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidStateSenderFactory.class);
	private final GameMain main;
	private final Player myPlayer;

	public AndroidStateSenderFactory(GameMain main, Player myPlayer) {
		super();
		this.main = main;
		this.myPlayer = myPlayer;
	}

	@Override
	public StateSender create(Player p) {
		if (this.main.existsRemoteConnection(p.getOpponent())) {
			LOGGER.info("Found network player. Creating gamenetworksender...");
			return new GameNetworkSender(this.myPlayer, this.main.findConnection(p.getOpponent()));
		}
		else {
			LOGGER.info("Found local player. DummyStateSender...");
			return new DummyStateSender(p);
		}
	}

}
