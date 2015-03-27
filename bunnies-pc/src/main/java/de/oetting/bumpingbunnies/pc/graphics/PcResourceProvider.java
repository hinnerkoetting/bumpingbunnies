package de.oetting.bumpingbunnies.pc.graphics;

import de.oetting.bumpingbunnies.core.game.ImageCache;
import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class PcResourceProvider implements ResourceProvider {

	private final ImageCache cache;

	public PcResourceProvider(ImageCache cache) {
		this.cache = cache;
	}

	@Override
	public ImageWrapper readBitmap(String fileName) {
		return cache.loadImage(fileName);
	}

}
