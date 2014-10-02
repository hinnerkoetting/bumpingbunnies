package de.oetting.bumpingbunnies.core.graphics;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public interface ImageResizer {

	ImageWrapper resize(ImageWrapper original, int targetWidth, int targetHeiht);
}
