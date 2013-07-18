package de.jumpnbump.usecases.viewer.Viewer;


public class CoordinatesCalculation {
	public static final int DIVIDER_X_CONST = 10000;
	public static final int DIVIDER_Y_CONST = 20000;

	public static int translateToGameX(int pixelX) {
		return pixelX * DIVIDER_X_CONST;
	}

	public static int translateToGameY(int pixelY, int heigth) {
		return (int) ((heigth * 0.9 - pixelY) * DIVIDER_Y_CONST);
	}

	public static int calculatePixelX(int origX) {
		return origX / DIVIDER_X_CONST;
	}

	public static int calculatePixelWidht(int minX, int maxX) {
		return calculatePixelX(maxX - minX);
	}

	public static int calculatePixelY(int origY, int height) {
		return (int) (height * 0.9 - origY / DIVIDER_Y_CONST);
	}

	public static int calculateHeight(int minY, int maxY) {
		return (maxY - minY) / DIVIDER_Y_CONST;
	}
}
