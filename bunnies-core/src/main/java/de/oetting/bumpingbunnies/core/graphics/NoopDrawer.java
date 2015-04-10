package de.oetting.bumpingbunnies.core.graphics;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class NoopDrawer implements Drawer {

	@Override
	public void newEvent(Bunny p) {
	}

	@Override
	public void removeEvent(Bunny p) {
	}

	@Override
	public void draw() {
	}

	@Override
	public void setNeedsUpdate(boolean b) {
	}

}
