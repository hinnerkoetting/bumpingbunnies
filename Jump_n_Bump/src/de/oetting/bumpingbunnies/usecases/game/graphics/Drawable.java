package de.oetting.bumpingbunnies.usecases.game.graphics;

public interface Drawable {

	void draw(CanvasDelegate canvas);

	/**
	 * Allows implementation to update cache, e.g. because of new size of screen
	 * or resized players.
	 */
	void updateGraphics(CanvasDelegate canvas);
}
