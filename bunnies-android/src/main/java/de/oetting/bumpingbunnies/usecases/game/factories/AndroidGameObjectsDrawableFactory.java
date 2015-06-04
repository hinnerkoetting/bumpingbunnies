package de.oetting.bumpingbunnies.usecases.game.factories;

import android.graphics.Bitmap;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.ImageMirroror;
import de.oetting.bumpingbunnies.core.game.graphics.RectDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.GameObjectDrawableFactory;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidImagesMirrorer;
import de.oetting.bumpingbunnies.usecases.game.graphics.ImageDrawerFactory;

public class AndroidGameObjectsDrawableFactory implements GameObjectDrawableFactory {

	private final ImageMirroror mirrored;

	public AndroidGameObjectsDrawableFactory() {
		this.mirrored = new AndroidImagesMirrorer();
	}

	@Override
	public Drawable create(GameObjectWithImage object, int objectWidth, int objectHeight) {
		if (object.getBitmap() != null) {
			Bitmap bitmap = (Bitmap) object.getBitmap().getBitmap();
			ImageWrapper imageWrapper = new ImageWrapper(bitmap, "");
			if (object.isMirroredHorizontally())
				imageWrapper = mirrored.mirrorImage(imageWrapper);
			return ImageDrawerFactory.create((Bitmap) imageWrapper.getBitmap(), object, objectWidth, objectHeight);
		} else {
			return new RectDrawer(object);
		}
	}

}
