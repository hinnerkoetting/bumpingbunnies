package de.oetting.bumpingbunnies.pc.game.network;

import de.oetting.bumpingbunnies.core.networking.DummyStateSender;
import de.oetting.bumpingbunnies.core.networking.StateSender;
import de.oetting.bumpingbunnies.core.networking.StateSenderFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PcStateSenderFactory implements StateSenderFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(PcStateSenderFactory.class);

	@Override
	public StateSender create(Player p) {
		LOGGER.info("Creating dummy sender");
		return new DummyStateSender(p);
	}
}
