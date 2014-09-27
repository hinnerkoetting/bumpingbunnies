package de.oetting.bumpingbunnies.core.graphics;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class NoopDrawer implements Drawer {

	@Override
	public void newPlayerJoined(Player p) {
	}

	@Override
	public void playerLeftTheGame(Player p) {
	}

	@Override
	public void draw() {
	}

	@Override
	public void setNeedsUpdate(boolean b) {
	}

}
