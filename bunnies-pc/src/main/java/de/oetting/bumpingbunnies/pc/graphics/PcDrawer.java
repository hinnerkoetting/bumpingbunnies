package de.oetting.bumpingbunnies.pc.graphics;

import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PcDrawer implements Drawer {

	private static final Logger LOGGER = LoggerFactory.getLogger(PcDrawer.class);

	@Override
	public void newPlayerJoined(Player p) {
		LOGGER.info("Player joined");
	}

	@Override
	public void playerLeftTheGame(Player p) {
		LOGGER.info("Player left");
	}

	@Override
	public void draw() {
		// nothing to do. JavaFx does it for us
	}

	@Override
	public void setNeedsUpdate(boolean b) {
	}

}
