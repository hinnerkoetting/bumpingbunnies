package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface Drawable {

	void draw(CanvasDelegate canvas);

	/**
	 * Should return true if the drawable draws the passed player.
	 */
	boolean drawsPlayer(Player p);
}
