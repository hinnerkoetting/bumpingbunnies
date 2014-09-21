package de.oetting.bumpingbunnies.pc.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.Image;

public class PcCanvasDelegate implements CanvasDelegate {

	private final CoordinatesCalculation coordinatesCalculation;
	private Canvas canvas;
	private PaintConverter paintConverter = new PaintConverter();

	public PcCanvasDelegate(CoordinatesCalculation coordinatesCalculation) {
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void updateDelegate(CanvasWrapper canvasWrapper) {
		canvas = (Canvas) canvasWrapper.getCanvasImpl();
	}

	@Override
	public void drawColor(int color) {
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		java.awt.Color awtColor = new java.awt.Color(color);
		javafx.scene.paint.Color paint = new javafx.scene.paint.Color(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(), awtColor.getAlpha());
		graphicsContext2D.setFill(paint);
		graphicsContext2D.fill();
	}

	@Override
	public void drawLine(int startX, int startY, int stopX, int stopY, Paint paint) {
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		graphicsContext2D.setStroke(paintConverter.convert(paint));
		graphicsContext2D.strokeLine(transformX(startX), transformY(startY), transformX(stopX), transformY(stopY));
	}

	@Override
	public void drawText(String text, int x, int y, Paint paint) {
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		graphicsContext2D.setStroke(paintConverter.convert(paint));
		graphicsContext2D.strokeText(text, transformX(x), transformY(y));
	}

	@Override
	public void drawTextRelativeToScreen(String text, double x, double y, Paint paint) {
		drawTextRelativeToScreen(text, x, y, paint);
	}

	@Override
	public void drawRect(long left, long top, long right, long bottom, Paint paint) {
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		graphicsContext2D.setFill(paintConverter.convert(paint));
		graphicsContext2D.fillRect(transformX(left), transformY(top), transformX(right - left), transformY(bottom - top));
	}

	@Override
	public void drawRectRelativeToScreen(double left, double top, double right, double bottom, Paint paint) {
		drawRect((long) left, (long) top, (long) right, (long) bottom, paint);
	}

	@Override
	public void drawImage(Image bitmap, long left, long top, Paint paint) {
		throw new IllegalArgumentException();
	}

	@Override
	public void drawImageDirect(Image bitmap, int left, int top, Paint paint) {
		throw new IllegalArgumentException();
	}

	@Override
	public float transformY(long y) {
		return coordinatesCalculation.getScreenCoordinateY(y);
	}

	@Override
	public float transformX(long x) {
		return coordinatesCalculation.getScreenCoordinateY(x);
	}

	@Override
	public int getOriginalWidth() {
		return (int) canvas.getWidth();
	}

	@Override
	public int getOriginalHeight() {
		return (int) canvas.getHeight();
	}

}
