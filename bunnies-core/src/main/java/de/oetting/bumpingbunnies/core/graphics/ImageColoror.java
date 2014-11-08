package de.oetting.bumpingbunnies.core.graphics;

public class ImageColoror {

	public static int colorPixel(int originalColor, int targetColor) {
		int gray = originalColor & 0xFF;
		if (gray < 200) {
			return originalColor & targetColor;
		} else {
			return originalColor;
		}
	}
}
