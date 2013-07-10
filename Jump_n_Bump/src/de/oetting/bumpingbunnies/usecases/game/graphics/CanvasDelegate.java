package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public interface CanvasDelegate {

	void updateDelegate(Canvas canvas);

	void drawColor(int color);

	void drawLine(int startX, int startY, int stopX, int stopY, Paint paint);

	void drawText(String text, int x, int y, Paint paint);

	void drawTextRelativeToScreen(String text, double x, double y, Paint paint);

	void drawRect(long left, long top, long right, long bottom, Paint paint);

	void drawRectRelativeToScreen(double left, double top, double right,
			double bottom, Paint paint);

	void drawImage(Bitmap bitmap, long left, long top, Paint paint);

	void drawImageDirect(Bitmap bitmap, int left, int top, Paint paint);

	float transformY(long y);

	float transformX(long x);

}