package de.oetting.bumpingbunnies.core.graphics;

import de.oetting.bumpingbunnies.usecases.game.model.Image;

public interface ImageResizer {

	Image resize(Image original, int targetWidth, int targetHeiht);
}
