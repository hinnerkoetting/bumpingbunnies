package de.jumpnbump.usecases.viewer.Viewer;

import de.jumpnbump.usecases.viewer.MyCanvas;

public class CoordinatesCalculation {
	public static final int DIVIDER_X_CONST = 10000;
	public static final int DIVIDER_Y_CONST = 20000;
	private final MyCanvas canvas;

	public CoordinatesCalculation(MyCanvas canvas) {
		super();
		this.canvas = canvas;
	}

	public int translateToGameX(double pixelX) {
		return (int) ((pixelX * DIVIDER_X_CONST) * this.canvas.getZoom());
	}

	public int translateToGameY(double pixelY, int heigth) {
		return (int) ((heigth * 0.9 - pixelY) * DIVIDER_Y_CONST * this.canvas.getZoom());
	}

	public int calculatePixelX(double origX) {
		return (int) (origX / DIVIDER_X_CONST / this.canvas.getZoom());
	}

	public int calculatePixelWidht(int minX, int maxX) {
		return calculatePixelX(maxX - minX);
	}

	public int calculatePixelY(double origY, int height) {
		return (int) ((height * 0.9) - origY / DIVIDER_Y_CONST / this.canvas.getZoom());
	}

	public int calculateHeight(int minY, int maxY, int heigth) {
		return -calculatePixelY(maxY, heigth) + calculatePixelY(minY, heigth);
	}
}
