package de.oetting.bumpingbunnies.core.game.graphics;

public interface DrawableFactory<S extends Drawable> {

	S create(int screenWidth, int screenHeight);
}
