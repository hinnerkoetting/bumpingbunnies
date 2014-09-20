package de.oetting.bumpingbunnies.core.graphics;

import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;

/**
 * Draws the current state of the game on the screen.
 *
 */
public interface Drawer extends PlayerJoinListener {

	void draw();

	void setNeedsUpdate(boolean b);

}
