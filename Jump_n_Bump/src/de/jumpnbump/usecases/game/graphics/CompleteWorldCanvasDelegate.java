package de.jumpnbump.usecases.game.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;

public class CompleteWorldCanvasDelegate implements CanvasDelegate {

	private Canvas canvas;
	private int width;
	private int height;

	@Override
	public void updateDelegate(Canvas canvas) {
		this.canvas = canvas;
		this.width = canvas.getWidth();
		this.height = canvas.getHeight();
	}

	@Override
	public void drawColor(int color) {
		this.canvas.drawColor(color);
	}

	@Override
	public void drawLine(int startX, int startY, int stopX, int stopY,
			Paint paint) {
		this.canvas.drawLine(transformX(startX), transformY(startY),
				transformX(stopX), transformY(stopY), paint);
	}

	@Override
	public void drawText(String text, int x, int y, Paint paint) {
		this.canvas.drawText(text, transformX(x), transformY(y), paint);
	}

	@Override
	public void drawRect(int left, int top, int right, int bottom, Paint paint) {
		this.canvas.drawRect(transformX(left), transformY(top),
				transformX(right), transformY(bottom), paint);
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	private float transformX(double x) {
		return GameToAndroidTransformation.transformX(x, this.width);
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	private float transformY(double y) {
		return GameToAndroidTransformation.transformY(y, this.height);
	}

}
