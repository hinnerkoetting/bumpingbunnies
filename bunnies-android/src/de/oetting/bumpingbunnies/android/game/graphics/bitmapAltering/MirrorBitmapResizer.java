package de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidImage;
import de.oetting.bumpingbunnies.usecases.game.model.Image;

public class MirrorBitmapResizer implements ImageResizer {

	@Override
	public Image resize(Image original, int targetWidth, int targetHeigth) {
		AndroidImage androidImage = (AndroidImage) original;
		Bitmap scaled = Bitmap.createScaledBitmap(androidImage.getBitmap(), targetWidth, targetHeigth, false);
		Matrix mirror = new Matrix();
		mirror.preScale(-1.0f, 1.0f);
		Bitmap mirroredBitmap = Bitmap.createBitmap(scaled, 0, 0, scaled.getWidth(), scaled.getHeight(), mirror, false);
		return new AndroidImage(mirroredBitmap);
	}

}
