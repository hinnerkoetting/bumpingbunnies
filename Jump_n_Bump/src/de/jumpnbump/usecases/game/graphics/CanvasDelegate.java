package de.jumpnbump.usecases.game.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;

public class CanvasDelegate {

	private Canvas canvas;
	private int width;
	private int height;

	public void updateDelegate(Canvas canvas) {
		this.canvas = canvas;
		this.width = canvas.getWidth();
		this.height = canvas.getHeight();
	}

	public void drawColor(int color) {
		this.canvas.drawColor(color);
	}

	public void drawLine(double startX, double startY, double stopX,
			double stopY, Paint paint) {
		this.canvas.drawLine(transformX(startX), transformY(startY),
				transformX(stopX), transformY(stopY), paint);
	}

	public void drawText(String text, double x, double y, Paint paint) {
		this.canvas.drawText(text, transformX(x), transformY(y), paint);
	}

	public void drawRect(double left, double top, double right, double bottom,
			Paint paint) {
		this.canvas.drawRect(transformX(left), transformY(top),
				transformX(right), transformY(bottom), paint);
	}

	private float transformX(double x) {
		return (float) (x * this.width);
	}

	private float transformY(double y) {
		return (float) ((1 - y) * this.height);
	}

}
