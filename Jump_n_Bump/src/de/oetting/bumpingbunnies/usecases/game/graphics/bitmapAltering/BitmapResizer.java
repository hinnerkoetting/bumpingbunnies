package de.oetting.bumpingbunnies.usecases.game.graphics.bitmapAltering;

import android.graphics.Bitmap;

public interface BitmapResizer {

	Bitmap resize(Bitmap original, int targetWidth, int targetHeiht);
}
