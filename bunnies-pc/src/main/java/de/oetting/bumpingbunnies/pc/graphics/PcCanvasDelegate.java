package de.oetting.bumpingbunnies.pc.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class PcCanvasDelegate implements CanvasDelegate {

	private Canvas canvas;
	private PaintConverter paintConverter = new PaintConverter();

	@Override
	public void updateDelegate(CanvasWrapper canvasWrapper) {
		canvas = (Canvas) canvasWrapper.getCanvasImpl();
	}

	@Override
	public void drawColor(Paint color) {
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		graphicsContext2D.setFill(paintConverter.convert(color));
		graphicsContext2D.fill();
	}

	@Override
	public void drawLine(long startX, long startY, long stopX, long stopY, Paint paint) {
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		graphicsContext2D.setStroke(paintConverter.convert(paint));
		graphicsContext2D.strokeLine(startX, startY, stopX, stopY);
	}

	@Override
	public void drawText(String text, long x, long y, Paint paint) {
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		graphicsContext2D.setStroke(paintConverter.convert(paint));
		graphicsContext2D.strokeText(text, x, y);
	}

	@Override
	public void drawTextRelativeToScreen(String text, double x, double y, Paint paint) {
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		graphicsContext2D.setStroke(paintConverter.convert(paint));
		graphicsContext2D.strokeText(text, (int) (x * getOriginalWidth()), (int) (y * getOriginalHeight()));
	}

	@Override
	public void drawRect(long left, long top, long right, long bottom, Paint paint) {
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		graphicsContext2D.setFill(paintConverter.convert(paint));
		int width = (int) (right - left);
		int height = (int) (bottom - top);
		graphicsContext2D.fillRect(left, top, width, height);
	}

	@Override
	public void drawRectRelativeToScreen(double left, double top, double right, double bottom, Paint paint) {
		drawRect((long) left, (long) top, (long) right, (long) bottom, paint);
	}

	@Override
	public void drawImageDirect(ImageWrapper bitmap, long left, long top, Paint paint) {
		drawImage(bitmap, left, top, paint);
	}

	@Override
	public void drawImage(ImageWrapper bitmap, long left, long top, Paint paint) {
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		Image image = (Image) bitmap.getBitmap();
		graphicsContext2D.drawImage(image, left, top);
	}

	@Override
	public int transformX(long x) {
		throw new IllegalArgumentException("Not capable");
	}

	@Override
	public int transformY(long y) {
		throw new IllegalArgumentException("Not capable");
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
