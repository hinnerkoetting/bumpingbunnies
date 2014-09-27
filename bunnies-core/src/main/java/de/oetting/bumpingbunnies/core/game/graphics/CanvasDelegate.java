package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public interface CanvasDelegate {

	void updateDelegate(CanvasWrapper canvas);

	void drawColor(Paint paint);

	void drawLine(int startX, int startY, int stopX, int stopY, Paint paint);

	void drawText(String text, int x, int y, Paint paint);

	void drawTextRelativeToScreen(String text, double x, double y, Paint paint);

	void drawRect(long left, long top, long right, long bottom, Paint paint);

	void drawRectRelativeToScreen(double left, double top, double right, double bottom, Paint paint);

	void drawImage(ImageWrapper bitmap, long left, long top, Paint paint);

	void drawImageDirect(ImageWrapper bitmap, int left, int top, Paint paint);

	float transformY(long y);

	float transformX(long x);

	int getOriginalWidth();

	int getOriginalHeight();

}