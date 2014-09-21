package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Canvas;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.Image;

public class AndroidCanvasDelegate implements CanvasDelegate {

	private Canvas canvas;
	private final CoordinatesCalculation calculations;
	private int width;
	private int heigth;
	private PaintConverter paintConverter = new PaintConverter();

	public AndroidCanvasDelegate(CoordinatesCalculation calculations) {
		this.calculations = calculations;
	}

	@Override
	public void updateDelegate(CanvasWrapper canvasWrapper) {
		this.canvas = (Canvas) canvasWrapper.getCanvasImpl();
		this.calculations.updateCanvas(canvas.getWidth(), canvas.getHeight());
		this.heigth = canvas.getHeight();
		this.width = canvas.getWidth();
	}

	@Override
	public void drawColor(Paint color) {
		this.canvas.drawColor(color.getColor());
	}

	@Override
	public void drawLine(int startX, int startY, int stopX, int stopY, Paint paint) {
		this.canvas.drawLine(transformX(startX), transformY(startY), transformX(stopX), transformY(stopY), paintConverter.convert(paint));
	}

	@Override
	public void drawText(String text, int x, int y, Paint paint) {
		this.canvas.drawText(text, transformX(x), transformY(y), paintConverter.convert(paint));
	}

	@Override
	public void drawRect(long left, long top, long right, long bottom, Paint paint) {
		this.canvas.drawRect(transformX(left), transformY(top), transformX(right), transformY(bottom), paintConverter.convert(paint));
	}

	@Override
	public void drawImage(Image bitmap, long left, long top, Paint paint) {
		AndroidImage androidImage = (AndroidImage) bitmap;
		this.canvas.drawBitmap(androidImage.getBitmap(), transformX(left), transformY(top), paintConverter.convert(paint));
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	@Override
	public float transformX(long x) {
		return this.calculations.getScreenCoordinateX(x);
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	@Override
	public float transformY(long y) {
		return this.calculations.getScreenCoordinateY(y);
	}

	@Override
	public void drawTextRelativeToScreen(String text, double x, double y, Paint paint) {
		this.canvas.drawText(text, (int) (x * this.width), (int) (y * this.heigth), paintConverter.convert(paint));

	}

	@Override
	public void drawRectRelativeToScreen(double left, double top, double right, double bottom, Paint paint) {
		this.canvas.drawRect((float) (left * this.width), (float) (top * this.heigth), (float) (right * this.width), (float) (bottom * this.heigth),
				paintConverter.convert(paint));
	}

	@Override
	public void drawImageDirect(Image bitmap, int left, int top, Paint paint) {
		AndroidImage androidImage = (AndroidImage) bitmap;
		this.canvas.drawBitmap(androidImage.getBitmap(), left, top, paintConverter.convert(paint));
	}

	@Override
	public int getOriginalWidth() {
		return this.width;
	}

	@Override
	public int getOriginalHeight() {
		return this.heigth;
	}

}
