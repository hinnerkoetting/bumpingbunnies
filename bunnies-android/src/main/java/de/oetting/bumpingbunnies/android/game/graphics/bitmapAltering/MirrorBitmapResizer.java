package de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class MirrorBitmapResizer implements ImageResizer {

	@Override
	public ImageWrapper resize(ImageWrapper original, int targetWidth, int targetHeight) {
		return new ImageWrapper(resize((Bitmap) original.getBitmap(), targetWidth, targetHeight));
	}

	public Bitmap resize(Bitmap original, int targetWidth, int targetHeigth) {
		if (targetHeigth < 0 || targetWidth < 0)
			throw new IllegalArgumentException(targetWidth + "/" + targetHeigth);
		Bitmap scaled = Bitmap.createScaledBitmap(original, targetWidth, targetHeigth, false);
		Matrix mirror = new Matrix();
		mirror.preScale(-1.0f, 1.0f);
		Bitmap mirroredBitmap = Bitmap.createBitmap(scaled, 0, 0, scaled.getWidth(), scaled.getHeight(), mirror, false);
		return mirroredBitmap;
	}

}
