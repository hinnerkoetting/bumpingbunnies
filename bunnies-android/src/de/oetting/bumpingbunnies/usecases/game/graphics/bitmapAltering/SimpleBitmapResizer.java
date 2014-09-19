package de.oetting.bumpingbunnies.usecases.game.graphics.bitmapAltering;

import android.graphics.Bitmap;

public class SimpleBitmapResizer implements BitmapResizer {

	@Override
	public Bitmap resize(Bitmap original, int targetWidth, int targetHeiht) {
		return Bitmap.createScaledBitmap(original, targetWidth, targetHeiht,
				false);
	}

}
