package de.oetting.bumpingbunnies.core.graphics;

import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public interface ImageResizer {

	ImageWrapper resize(ImageWrapper original, int targetWidth, int targetHeiht);
}
