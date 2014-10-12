package de.oetting.bumpingbunnies.core.graphics;

import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.game.main.OneLoopStep;
import de.oetting.bumpingbunnies.core.game.main.ThreadLoop;
import de.oetting.bumpingbunnies.model.game.objects.Player;

/**
 * Delegates drawing to drawer. Counts fps.
 *
 */
public class DrawerFpsCounter implements Drawer {

	private final Drawer drawer;
	private final ThreadLoop loop;

	public DrawerFpsCounter(Drawer drawer, GameThreadState state) {
		this.drawer = drawer;
		loop = new ThreadLoop(new OneLoopStep() {

			@Override
			public void nextStep(long delta) {
				DrawerFpsCounter.this.drawer.draw();
			}
		}, 60, state);
	}

	@Override
	public void newEvent(Player p) {
		drawer.newEvent(p);
	}

	@Override
	public void removeEvent(Player p) {
		drawer.removeEvent(p);
	}

	@Override
	public void draw() {
		loop.nextStep();
	}

	@Override
	public void setNeedsUpdate(boolean b) {
		drawer.setNeedsUpdate(b);
	}

}
