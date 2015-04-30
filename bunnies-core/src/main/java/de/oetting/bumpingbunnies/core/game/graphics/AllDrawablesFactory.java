package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class AllDrawablesFactory {

	private final DrawableToImageConverter drawer;

	public AllDrawablesFactory(DrawableToImageConverter drawer) {
		this.drawer = drawer;
	}

	public Drawable createImagesWhichContainsAllElements(List<Drawable> drawable) {
		 ImageWrapper drawOnImage = drawer.drawOnImage(drawable);
		 return new BackgroundDrawer(drawOnImage, true);
	}
}
