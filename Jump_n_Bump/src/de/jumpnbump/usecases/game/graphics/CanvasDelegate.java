package de.jumpnbump.usecases.game.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import de.jumpnbump.usecases.game.model.ModelConstants;

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

	public void drawLine(int startX, int startY, int stopX, int stopY,
			Paint paint) {
		this.canvas.drawLine(transformX(startX), transformY(startY),
				transformX(stopX), transformY(stopY), paint);
	}

	public void drawText(String text, int x, int y, Paint paint) {
		this.canvas.drawText(text, transformX(x), transformY(y), paint);
	}

	public void drawRect(int left, int top, int right, int bottom, Paint paint) {
		this.canvas.drawRect(transformX(left), transformY(top),
				transformX(right), transformY(bottom), paint);
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	private float transformX(double x) {
		return (float) ((x * this.width) / ModelConstants.MAX_VALUE);
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	private float transformY(double y) {
		return (float) (((ModelConstants.MAX_VALUE - y) * this.height) / ModelConstants.MAX_VALUE);
	}

}
