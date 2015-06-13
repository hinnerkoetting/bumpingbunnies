package de.oetting.bumpingbunnies.core.graphics;

public class ImageColoror {

	public static int colorPixel(int originalColor, int targetColor) {
		int newA =  (int) ((getA(originalColor) / 256.0 * 1) * 256.0);
		int newR = (int) ((getR(originalColor) / 256.0 * getR(targetColor) / 256.0) * 256.0);
		int newG = (int) ((getG(originalColor) / 256.0 * getG(targetColor) / 256.0) * 256.0);
		int newB = (int) ((getB(originalColor) / 256.0 * getB(targetColor) / 256.0) * 256.0);
		int gray = originalColor & 0xFF;
		if (gray < 200) {
			return (newA << 24) + (newR << 16) + (newG << 8) + newB;
		} else {
			//do not color white pixels like the eyes
			return originalColor;
		}
	}

	private static int getA(int originalColor) {
		return (originalColor & 0xFF000000) >>> 24;
	}

	private static int getR(int originalColor) {
		return (originalColor & 0x00FF0000) >>> 16;
	}

	private static int getG(int originalColor) {
		return (originalColor & 0x0000FF00) >>> 8;
	}

	private static int getB(int originalColor) {
		return (originalColor & 0x000000FF);
	}
}
