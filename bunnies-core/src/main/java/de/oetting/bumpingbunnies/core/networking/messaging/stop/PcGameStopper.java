package de.oetting.bumpingbunnies.core.networking.messaging.stop;

import javafx.application.Platform;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class PcGameStopper implements GameStopper {

	private static final Logger LOGGER = LoggerFactory.getLogger(PcGameStopper.class);

	@Override
	public void stopGame() {
		Platform.exit();
	}

	@Override
	public void onDisconnect() {
		LOGGER.info("Player disconnect: ending game...");
		Platform.exit();
	}

}
