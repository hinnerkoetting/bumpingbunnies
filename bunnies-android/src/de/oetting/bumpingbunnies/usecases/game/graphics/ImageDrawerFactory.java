package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Bitmap;
import de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering.SimpleBitmapResizer;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public class ImageDrawerFactory {

	public static ImageDrawer create(Bitmap bitmap, GameObject gameObject) {
		return new ImageDrawer(new ImageWrapper(bitmap), gameObject, new SimpleBitmapResizer());
	}
}
