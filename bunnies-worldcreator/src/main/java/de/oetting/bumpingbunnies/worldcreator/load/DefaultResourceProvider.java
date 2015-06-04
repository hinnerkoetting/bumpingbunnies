package de.oetting.bumpingbunnies.worldcreator.load;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class DefaultResourceProvider implements ResourceProvider {

	private final ImageCache cache;

	public DefaultResourceProvider(ImageCache cache) {
		this.cache = cache;
	}

	@Override
	public ImageWrapper readBitmap(String fileName) {
		return cache.loadImage(fileName);
	}

}
