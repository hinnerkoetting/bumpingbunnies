package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.RectDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.GameObjectDrawableFactory;
import de.oetting.bumpingbunnies.core.graphics.ImageDrawer;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.usecases.game.model.GameObjectWithImage;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public class PcGameObjectDrawableFactory implements GameObjectDrawableFactory {

	private final ImageResizer resizer;

	public PcGameObjectDrawableFactory() {
		super();
		this.resizer = new PcImagesResizer();
	}

	@Override
	public Drawable create(GameObjectWithImage object, int objectWidth, int objectHeight) {
		if (object.getBitmap() != null) {
			ImageWrapper resizedImage = resizer.resize(object.getBitmap(), objectWidth, objectHeight);
			return new ImageDrawer(resizedImage, object);
		} else {
			return new RectDrawer(object);
		}
	}
}
