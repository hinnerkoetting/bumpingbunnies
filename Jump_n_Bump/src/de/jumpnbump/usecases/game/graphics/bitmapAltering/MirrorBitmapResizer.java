package de.jumpnbump.usecases.game.graphics.bitmapAltering;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class MirrorBitmapResizer implements BitmapResizer {

	@Override
	public Bitmap resize(Bitmap original, int targetWidth, int targetHeigth) {
		Bitmap scaled = Bitmap.createScaledBitmap(original, targetWidth,
				targetHeigth, false);
		Matrix mirror = new Matrix();
		mirror.preScale(-1.0f, 1.0f);
		Bitmap mirroredBitmap = Bitmap.createBitmap(scaled, 0, 0,
				scaled.getWidth(), scaled.getHeight(), mirror, false);
		return mirroredBitmap;
	}

}
