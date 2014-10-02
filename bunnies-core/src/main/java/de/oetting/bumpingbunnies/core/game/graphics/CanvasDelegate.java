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

	void drawRectRelativeToScreen(double left, double top, double right, double bottom, Paint paint);

	void drawImage(ImageWrapper bitmap, long left, long top, Paint paint);

	void drawImageDirect(ImageWrapper bitmap, long left, long top, Paint paint);

	int transformY(long gameY);

	int transformX(long gameX);

	int getOriginalWidth();

	int getOriginalHeight();

}