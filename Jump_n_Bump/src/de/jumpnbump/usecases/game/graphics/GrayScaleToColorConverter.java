package de.jumpnbump.usecases.game.graphics;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class GrayScaleToColorConverter {

	public static Bitmap convertToColor(Bitmap origin, int color) {
		Bitmap target = Bitmap.createBitmap(origin.getWidth(),
				origin.getHeight(), Config.ARGB_8888);
		for (int i = 0; i < origin.getWidth(); i++) {
			for (int j = 0; j < origin.getHeight(); j++) {
				int origColor = origin.getPixel(i, j);
				int gray = origColor & 0xFF;
				if (gray < 200) {
					int targetColor = origColor & color;
					target.setPixel(i, j, targetColor);
				} else {
					target.setPixel(i, j, origColor);
				}
			}
		}
		return target;
	}
}