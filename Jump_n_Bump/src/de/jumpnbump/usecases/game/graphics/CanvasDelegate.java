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
		this.canvas.drawLine((float) (startX * this.width),
				(float) (startY * this.height), (float) (stopX * this.width),
				(float) (stopY * this.width), paint);
	}

	public void drawText(String text, double x, double y, Paint paint) {
		this.canvas.drawText(text, (float) (x * this.width),
				(float) (y * this.height), paint);
	}

	public void drawRect(double left, double top, double right, double bottom,
			Paint paint) {
		this.canvas.drawRect((float) (left * this.width),
				(float) (top * this.height), (float) (right * this.width),
				(float) (bottom * this.height), paint);
	}

}
