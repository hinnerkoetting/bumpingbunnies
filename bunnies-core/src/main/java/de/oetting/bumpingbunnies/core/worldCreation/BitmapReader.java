package de.oetting.bumpingbunnies.core.worldCreation;

import de.oetting.bumpingbunnies.usecases.game.model.Image;

public interface BitmapReader {

	public abstract Image readBitmap(String filename);

}
