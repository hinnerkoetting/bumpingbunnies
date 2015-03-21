package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

/**
 * Translates game coordinates to canvas coordinates.
 *
 */
public class CanvasCoordinateTranslator implements CanvasDelegate {

	private final CanvasDelegate next;
	private final CoordinatesCalculation coordinatesCalculation;

	public CanvasCoordinateTranslator(CanvasDelegate next, CoordinatesCalculation coordinatesCalculation) {
		this.next = next;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void updateDelegate(CanvasWrapper canvas) {
		coordinatesCalculation.updateCanvas(canvas.getCanvasWidth(), canvas.getCanvasHeight());
		next.updateDelegate(canvas);
	}

	@Override
	public void drawColor(Paint paint) {
		next.drawColor(paint);
	}

	@Override
	public void drawLine(long startX, long startY, long stopX, long stopY, Paint paint) {
		next.drawLine(startX, transformX(startX), transformX(stopX), transformY(stopY), paint);
	}

	@Override
	public void drawText(String text, long x, long y, Paint paint) {
		next.drawText(text, transformX(x), transformY(y), paint);
	}

	@Override
	public void drawTextRelativeToScreen(String text, double x, double y, Paint paint) {
		next.drawTextRelativeToScreen(text, x, y, paint);
	}

	@Override
	public void drawRect(long left, long top, long right, long bottom, Paint paint) {
		next.drawRect(transformX(left), transformY(top), transformX(right), transformY(bottom), paint);
	}

	@Override
	public void drawRectRelativeToScreen(double left, double top, double right, double bottom, Paint paint) {
		next.drawRectRelativeToScreen(left, top, right, bottom, paint);
	}

	@Override
	public void drawImage(ImageWrapper bitmap, long left, long top, Paint paint) {
		next.drawImage(bitmap, transformX(left), transformY(top), paint);
	}

	@Override
	public void drawImageDirect(ImageWrapper bitmap, long left, long top, Paint paint) {
		next.drawImageDirect(bitmap, left, top, paint);
	}

	@Override
	public int getOriginalWidth() {
		return next.getOriginalWidth();
	}

	@Override
	public int getOriginalHeight() {
		return next.getOriginalHeight();
	}

	@Override
	public int transformX(long x) {
		return coordinatesCalculation.getScreenCoordinateX(x);
	}

	@Override
	public int transformY(long y) {
		return coordinatesCalculation.getScreenCoordinateY(y);
	}

	@Override
	public void startDrawPhase() {
		coordinatesCalculation.fixCurrentLocation();
	}

	@Override
	public void endDrawPhase() {
		coordinatesCalculation.resetCurrentLocation();
	}

	@Override
	public boolean isVisible(long centerX, long centerY) {
		return next.isVisible(transformX(centerX), transformY(centerY));
	}

	@Override
	public void drawRectAbsoluteScreen(int left, int top, int right, int bottom, Paint paint) {
		next.drawRectAbsoluteScreen(left, top, right, bottom, paint);
	}

	@Override
	public boolean isVisibleX(long centerX) {
		return next.isVisibleX(transformX(centerX));
	}

	@Override
	public boolean isVisibleY(long centerY) {
		return next.isVisibleY(centerY); 
	}

	@Override
	public int getTextHeight(String text, Paint paint) {
		return next.getTextHeight(text, paint);
	}

	@Override
	public int getTextWidth(String text, Paint paint) {
		return next.getTextWidth(text, paint);
	}

}
