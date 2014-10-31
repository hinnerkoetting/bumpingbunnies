package de.oetting.bumpingbunnies.model.game.objects;

public interface GameObjectWithImage extends GameObjectWithColor, ImageContainer {

	String getImageKey();

	void applyImage(ImageWrapper wrapper);

}
