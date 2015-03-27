package de.oetting.bumpingbunnies.android.xml.parsing;

import de.oetting.bumpingbunnies.core.game.ImageCache;
import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class AndroidResourceProvider implements ResourceProvider {

	private final ImageCache imageCache;

	public AndroidResourceProvider(ImageCache imageCache) {
		this.imageCache = imageCache;
	}

	@Override
	public ImageWrapper readBitmap(String fileName) {
		return imageCache.loadImage(fileName);
	}

}
