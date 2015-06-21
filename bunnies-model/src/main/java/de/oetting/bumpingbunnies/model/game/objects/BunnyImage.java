package de.oetting.bumpingbunnies.model.game.objects;

public class BunnyImage {

	private final ImageWrapper image;
	private final BunnyImageModel model;

	public BunnyImage(ImageWrapper image, BunnyImageModel model) {
		this.image = image;
		this.model = model;
	}

	public ImageWrapper getImage() {
		return image;
	}

	public BunnyImageModel getModel() {
		return model;
	}

}
