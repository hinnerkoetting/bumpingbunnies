package de.oetting.bumpingbunnies.core.worldCreation;

import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public interface BitmapReader {

	ImageWrapper readBitmap(String filename);

}
