package de.oetting.bumpingbunnies.model.game.objects;

public class ImageWrapper {

	private final Object image;

	public ImageWrapper(Object image) {
		this.image = image;
	}

	public Object getBitmap() {
		return image;
	}

}
