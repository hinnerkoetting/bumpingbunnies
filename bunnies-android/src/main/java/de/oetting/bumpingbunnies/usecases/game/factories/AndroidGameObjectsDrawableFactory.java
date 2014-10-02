package de.oetting.bumpingbunnies.usecases.game.factories;

import android.graphics.Bitmap;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.RectDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.GameObjectDrawableFactory;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.usecases.game.graphics.ImageDrawerFactory;

public class AndroidGameObjectsDrawableFactory implements GameObjectDrawableFactory {

	@Override
	public Drawable create(GameObjectWithImage object, int objectWidth, int objectHeight) {
		if (object.getBitmap() != null) {
			Bitmap bitmap = (Bitmap) object.getBitmap().getBitmap();
			return ImageDrawerFactory.create(bitmap, object, objectWidth, objectHeight);
		} else {
			return new RectDrawer(object);
		}
	}

}
