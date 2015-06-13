package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.ImageMirroror;
import de.oetting.bumpingbunnies.core.game.graphics.ClearBackgroundDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.RectDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.GameObjectDrawableFactory;
import de.oetting.bumpingbunnies.core.graphics.ImageDrawer;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class PcGameObjectDrawableFactory implements GameObjectDrawableFactory {

	private final ImageResizer resizer;
	private final ImageMirroror mirrored;

	public PcGameObjectDrawableFactory(ImageMirroror mirroror) {
		this.resizer = new PcImagesResizer();
		mirrored = mirroror;
	}

	@Override
	public Drawable create(GameObjectWithImage object, int objectWidth, int objectHeight) {
		if (object.getBitmap() != null) {
			ImageWrapper resizedImage = resizer.resize(object.getBitmap(), objectWidth, objectHeight);
			if (object.isMirroredHorizontally()) {
				resizedImage = mirrored.mirrorImage(resizedImage);
			}
			return new ImageDrawer(resizedImage, object);
		} else {
			return new RectDrawer(object);
		}
	}
}
