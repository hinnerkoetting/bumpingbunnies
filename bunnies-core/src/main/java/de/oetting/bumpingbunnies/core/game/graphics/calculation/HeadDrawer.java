package de.oetting.bumpingbunnies.core.game.graphics.calculation;

import de.oetting.bumpingbunnies.model.game.objects.BunnyImageModel;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public interface HeadDrawer {

	ImageWrapper overDrawFace(ImageWrapper originalImage, ImageWrapper drawOverImage, BunnyImageModel model);
}
