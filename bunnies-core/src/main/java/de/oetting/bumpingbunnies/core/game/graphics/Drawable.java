package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public interface Drawable {

	void draw(CanvasAdapter canvas);

	/**
	 * Should return true if the drawable draws the passed player.
	 */
	boolean drawsPlayer(Bunny p);
}
