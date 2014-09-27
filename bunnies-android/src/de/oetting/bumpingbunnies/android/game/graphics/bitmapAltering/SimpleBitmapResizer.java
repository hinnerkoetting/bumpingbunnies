package de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering;

import android.graphics.Bitmap;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public class SimpleBitmapResizer implements ImageResizer {

	@Override
	public ImageWrapper resize(ImageWrapper original, int targetWidth, int targetHeiht) {
		Bitmap scaledBitmap = Bitmap.createScaledBitmap((Bitmap) original.getBitmap(), targetWidth, targetHeiht, false);
		return new ImageWrapper(scaledBitmap);
	}

}
