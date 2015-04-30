package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public interface CanvasAdapter {

	ImageWrapper drawOnImage(List<Drawable> drawables);

}
