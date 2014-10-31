package de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering;

import android.graphics.Bitmap;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class SimpleBitmapResizer implements ImageResizer {

	@Override
	public ImageWrapper resize(ImageWrapper original, int targetWidth, int targetHeight) {
		return new ImageWrapper(resize((Bitmap) original.getBitmap(), targetWidth, targetHeight), original.getImageKey());
	}

	public Bitmap resize(Bitmap original, int targetWidth, int targetHeight) {
		if (targetHeight < 0 || targetWidth < 0)
			throw new IllegalArgumentException(targetWidth + "/" + targetHeight);
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(original, targetWidth, targetHeight, false);
		return scaledBitmap;
	}

}
