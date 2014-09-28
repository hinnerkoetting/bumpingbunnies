package de.oetting.bumpingbunnies.core.game;

import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class NoopGameStopper implements GameStopper {

	private static final Logger LOGGER = LoggerFactory.getLogger(NoopGameStopper.class);

	@Override
	public void stopGame() {
		LOGGER.info("wanting to stop game. Doing nothing...");
	}

	@Override
	public void onDisconnect() {
		LOGGER.info("on disconnect. Doing nothing...");
	}

}
