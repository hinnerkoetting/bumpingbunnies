package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Bitmap;
import de.oetting.bumpingbunnies.android.game.graphics.SimpleBitmapResizer;
import de.oetting.bumpingbunnies.core.graphics.ImageDrawer;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithColor;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class ImageDrawerFactory {

	public static ImageDrawer create(Bitmap bitmap, GameObjectWithColor gameObject, int screenWidth, int screenHeight) {
		SimpleBitmapResizer resizer = new SimpleBitmapResizer();
		ImageWrapper wrapper = new ImageWrapper(bitmap, "");
		ImageWrapper resizedImage = resizer.resize(wrapper, screenWidth, screenHeight);
		return new ImageDrawer(resizedImage, gameObject);
	}
}
