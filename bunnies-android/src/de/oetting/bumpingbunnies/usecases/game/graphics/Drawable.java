package de.oetting.bumpingbunnies.usecases.game.graphics;

import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface Drawable {

	void draw(CanvasDelegate canvas);

	/**
	 * Allows implementation to update cache, e.g. because of new size of screen or resized players.
	 */
	void updateGraphics(CanvasDelegate canvas);

	/**
	 * Should return true if the drawable draws the passed player.
	 */
	boolean drawsPlayer(Player p);
}
