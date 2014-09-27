package de.oetting.bumpingbunnies.core.game.graphics.calculation;

import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public interface ImagesColorer {

	ImageWrapper colorImage(ImageWrapper image, int color);
}
