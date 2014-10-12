package de.oetting.bumpingbunnies.core.graphics;

import de.oetting.bumpingbunnies.model.game.objects.Player;

public class NoopDrawer implements Drawer {

	@Override
	public void newEvent(Player p) {
	}

	@Override
	public void removeEvent(Player p) {
	}

	@Override
	public void draw() {
	}

	@Override
	public void setNeedsUpdate(boolean b) {
	}

}
