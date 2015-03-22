package de.oetting.bumpingbunnies.pc.graphics;

import static javafx.scene.text.Font.font;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
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
		graphicsContext2D.setFont(font("Verdana", paint.getTextSize()));
		graphicsContext2D.setStroke(paintConverter.convert(paint));
		graphicsContext2D.setFill(paintConverter.convert(paint));
		graphicsContext2D.fillText(text, (int) (x * getOriginalWidth()), (int) (y * getOriginalHeight()));
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
		drawRect((long) (left * getOriginalWidth()), (long) (top * getOriginalHeight()), (long) (right * getOriginalWidth()), (long) (bottom * getOriginalHeight()), paint);
	}

	@Override
	public void drawImageDirect(ImageWrapper bitmap, long left, long top, Paint paint) {
		drawImage(bitmap, left, top, paint);
	}

	@Override
	public void drawImage(ImageWrapper bitmap, long left, long top, Paint paint) {
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		Image image = (Image) bitmap.getBitmap();
		if (paint.getAlpha() < 255) {
			ColorAdjust adjust = new ColorAdjust();
			// simulate alpha effect. maybe there is a better way?
			adjust.setBrightness((255 - paint.getAlpha()) / 255.0 / 2);
			graphicsContext2D.setEffect(adjust);
			graphicsContext2D.drawImage(image, left, top);
			graphicsContext2D.setEffect(null);
		} else {
			graphicsContext2D.drawImage(image, left, top);
		}
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

	@Override
	public void startDrawPhase() {
	}

	@Override
	public void endDrawPhase() {
	}

	@Override
	public boolean isVisible(long centerX, long centerY) {
		boolean xVisible = isVisibleX(centerX);
		boolean yVisible = isVisibleY(centerY);
		return xVisible && yVisible;
	}

	
	@Override
	public boolean isVisibleX(long centerX) {
		return centerX >= 0 && centerX <= getOriginalWidth();
	}
	
	@Override
	public boolean isVisibleY(long centerY) {
		return centerY >= 0 && centerY <= getOriginalHeight();
	}

	@Override
	public void drawRectAbsoluteScreen(int left, int top, int right, int bottom, Paint paint) {
		drawRect(left, top, right, bottom, paint);
	}

	@Override
	public int getTextHeight(String text, Paint paint) {
		return (int) paint.getTextSize();
	}

	@Override
	public int getTextWidth(String text, Paint paint) {
		return text.length() *  16;
	}

}
