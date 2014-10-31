package de.oetting.bumpingbunnies.model.game.objects;

public class ImageWrapper {

	private final Object image;
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
