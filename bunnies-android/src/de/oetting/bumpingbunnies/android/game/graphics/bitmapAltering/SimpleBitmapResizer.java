package de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering;

import android.graphics.Bitmap;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidImage;
import de.oetting.bumpingbunnies.usecases.game.model.Image;

public class SimpleBitmapResizer implements ImageResizer {

	@Override
	public Image resize(Image original, int targetWidth, int targetHeiht) {
		AndroidImage androidImage = (AndroidImage) original;
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(androidImage.getBitmap(), targetWidth, targetHeiht, false);
		return new AndroidImage(scaledBitmap);
	}

}
