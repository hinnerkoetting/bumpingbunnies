package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class AndroidCanvasDelegate implements CanvasDelegate {

	private Canvas canvas;
	private int width;
	private int heigth;
	private PaintConverter paintConverter = new PaintConverter();

	@Override
	public void updateDelegate(CanvasWrapper canvasWrapper) {
		this.canvas = (Canvas) canvasWrapper.getCanvasImpl();
		this.heigth = canvas.getHeight();
		this.width = canvas.getWidth();
	}

	@Override
	public void drawColor(Paint color) {
		this.canvas.drawColor(color.getColor());
	}

	@Override
	public void drawLine(long startX, long startY, long stopX, long stopY, Paint paint) {
		this.canvas.drawLine(startX, startY, stopX, stopY, paintConverter.convert(paint));
	}

	@Override
	public void drawText(String text, long x, long y, Paint paint) {
		this.canvas.drawText(text, x, y, paintConverter.convert(paint));
	}

	@Override
	public void drawRect(long left, long top, long right, long bottom, Paint paint) {
		this.canvas.drawRect(left, top, right, bottom, paintConverter.convert(paint));
	}

	@Override
	public void drawImage(ImageWrapper bitmap, long left, long top, Paint paint) {
		this.canvas.drawBitmap((Bitmap) bitmap.getBitmap(), left, top, paintConverter.convert(paint));
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
	public void drawTextRelativeToScreen(String text, double x, double y, Paint paint) {
		this.canvas.drawText(text, (int) (x * this.width), (int) (y * this.heigth), paintConverter.convert(paint));

	}

	@Override
	public void drawRectRelativeToScreen(double left, double top, double right, double bottom, Paint paint) {
		this.canvas.drawRect((float) (left * this.width), (float) (top * this.heigth), (float) (right * this.width),
				(float) (bottom * this.heigth), paintConverter.convert(paint));
	}

	@Override
	public void drawImageDirect(ImageWrapper bitmap, long left, long top, Paint paint) {
		this.canvas.drawBitmap((Bitmap) bitmap.getBitmap(), left, top, paintConverter.convert(paint));
	}

	@Override
	public int getOriginalWidth() {
		return this.width;
	}

	@Override
	public int getOriginalHeight() {
		return this.heigth;
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
		return centerX >= 0 && centerX <= width;
	}
	
	@Override
	public boolean isVisibleY(long centerY) {
		return centerY >= 0 && centerY <= heigth;
	}

	@Override
	public void drawRectAbsoluteScreen(int left, int top, int right, int bottom, Paint paint) {
		this.canvas.drawRect(left, top, right, bottom, paintConverter.convert(paint));
	}

}
