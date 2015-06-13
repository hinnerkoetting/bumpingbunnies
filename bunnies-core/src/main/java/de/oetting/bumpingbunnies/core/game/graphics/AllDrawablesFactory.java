package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.List;

import de.oetting.bumpingbunnies.core.graphics.ImageDrawer;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Wall;

public class AllDrawablesFactory {

	private final DrawableToImageConverter drawer;

	public AllDrawablesFactory(DrawableToImageConverter drawer) {
		this.drawer = drawer;
	}

	public Drawable createImagesWhichContainsAllElements(List<Drawable> drawable) {
		ImageWrapper drawOnImage = drawer.drawOnImage(drawable);
		return new ImageDrawer(drawOnImage, new Wall(-1, 0, 0, ModelConstants.STANDARD_WORLD_SIZE,
				ModelConstants.STANDARD_WORLD_SIZE));
		// return new BackgroundDrawer(drawOnImage);
	}
}
