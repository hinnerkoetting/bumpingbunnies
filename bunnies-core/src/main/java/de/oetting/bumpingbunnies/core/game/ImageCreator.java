package de.oetting.bumpingbunnies.core.game;

import java.io.InputStream;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public interface ImageCreator {

	ImageWrapper createImage(InputStream inputStream, String imageKey);
}
