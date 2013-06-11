package de.jumpnbump.usecases.game.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import de.jumpnbump.usecases.game.android.calculation.RelativeCoordinatesCalculation;

public final class CanvasDelegateImpl implements CanvasDelegate {

	private Canvas canvas;
	private final RelativeCoordinatesCalculation calculations;

	public CanvasDelegateImpl(RelativeCoordinatesCalculation calculations) {
		this.calculations = calculations;
	}

	@Override
	public void updateDelegate(Canvas canvas) {
		this.canvas = canvas;
		this.calculations.updateCanvas(canvas.getWidth(), canvas.getHeight());
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

	@Override
	public void drawImage(Bitmap bitmap, int left, int top, Paint paint) {
		this.canvas
				.drawBitmap(bitmap, transformX(left), transformY(top), paint);
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	@Override
	public float transformX(int x) {
		return this.calculations.getScreenCoordinateX(x);
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	@Override
	public float transformY(int y) {
		return this.calculations.getScreenCoordinateY(y);
	}

}
