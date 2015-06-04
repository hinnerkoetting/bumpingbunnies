package de.oetting.bumpingbunnies.worldcreator.load;

import java.util.HashMap;
import java.util.Map;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class ImageCache {

	private final Map<String, ImageWrapper> imagesCache = new HashMap<String, ImageWrapper>();

	public void addImage(ImageWrapper image) {
		imagesCache.put(image.getImageKey(), image);
	}
	
	public ImageWrapper loadImage(String key) {
		return imagesCache.get(key);
	}
}
