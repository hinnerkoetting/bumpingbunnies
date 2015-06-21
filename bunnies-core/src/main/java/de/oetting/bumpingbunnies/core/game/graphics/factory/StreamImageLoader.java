package de.oetting.bumpingbunnies.core.game.graphics.factory;

import java.io.InputStream;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public interface StreamImageLoader {

	ImageWrapper loadImage(InputStream inputStream, int width, int height);
	ImageWrapper loadImage(InputStream inputStream);
}
