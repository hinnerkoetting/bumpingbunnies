package de.oetting.bumpingbunnies.core.graphics;

import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.game.main.OneLoopStep;
import de.oetting.bumpingbunnies.core.game.main.ThreadLoop;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

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
		}, 30, state);
	}

	@Override
	public void newPlayerJoined(Player p) {
		drawer.newPlayerJoined(p);
	}

	@Override
	public void playerLeftTheGame(Player p) {
		drawer.playerLeftTheGame(p);
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