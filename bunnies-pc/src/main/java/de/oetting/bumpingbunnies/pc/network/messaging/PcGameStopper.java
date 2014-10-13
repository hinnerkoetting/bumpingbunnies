package de.oetting.bumpingbunnies.pc.network.messaging;

import javafx.application.Platform;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.OnThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class PcGameStopper implements OnThreadErrorCallback {

	private static final Logger LOGGER = LoggerFactory.getLogger(PcGameStopper.class);

	@Override
	public void onThreadError() {
		Platform.exit();
	}

	@Override
	public void onDisconnect() {
		LOGGER.info("Player disconnect: ending game...");
		Platform.exit();
	}

}
