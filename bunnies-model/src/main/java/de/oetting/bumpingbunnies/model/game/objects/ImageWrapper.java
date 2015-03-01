package de.oetting.bumpingbunnies.model.game.objects;

public class ImageWrapper {

	private final Object image;
	/**
	 * Reference of the image, which is used in levels.
	 */
	private final String key;

	public ImageWrapper(Object image, String key) {
		this.image = image;
		this.key = key;
	}

	public Object getBitmap() {
		return image;
	}

	public String getImageKey() {
		return key;
	}

}
