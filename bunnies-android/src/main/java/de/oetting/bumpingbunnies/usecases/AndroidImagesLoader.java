package de.oetting.bumpingbunnies.usecases;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.android.game.graphics.SimpleBitmapResizer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.StreamImageLoader;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class AndroidImagesLoader implements StreamImageLoader {

	private SimpleBitmapResizer imageResizer = new SimpleBitmapResizer();

	@Override
	public ImageWrapper loadImage(InputStream inputStream, int width, int height) {
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		return new ImageWrapper(imageResizer.resize(bitmap, width, height), "");
	}

}
