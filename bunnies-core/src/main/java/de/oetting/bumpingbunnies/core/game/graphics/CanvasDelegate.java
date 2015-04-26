package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public interface CanvasDelegate {

	void updateDelegate(CanvasWrapper canvas);

	void drawColor(Paint paint);

	void drawLine(long startX, long startY, long stopX, long stopY, Paint paint);

	void drawText(String text, long x, long y, Paint paint);

	void drawTextRelativeToScreen(String text, double x, double y, Paint paint);

	void drawRect(long left, long top, long right, long bottom, Paint paint);

	/**
	 * Does not draw in game coordinates but relative to the screen. This should be used for drawings which are situated at a fixed position on the screen.
	 */
	void drawRectRelativeToScreen(double left, double top, double right, double bottom, Paint paint);
	
	void drawRectAbsoluteScreen(int left, int top, int right, int bottom, Paint paint);

	void drawImage(ImageWrapper bitmap, long left, long top, Paint paint);

	void drawImageDirect(ImageWrapper bitmap, long left, long top, Paint paint);

	int transformY(long gameY);

	int transformX(long gameX);

	int getOriginalWidth();

	int getOriginalHeight();

	void startDrawPhase();

	void endDrawPhase();

	boolean isVisible(long centerX, long centerY);

	boolean isVisibleX(long centerX);

	boolean isVisibleY(long centerY);

	int getTextHeight(String text, Paint paint);

	int getTextWidth(String text, Paint paint);

	int getWidth(ImageWrapper imageWrapper);

	int getHeight(ImageWrapper imageWrapper);

}