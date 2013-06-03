package de.jumpnbump.usecases.game.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public interface CanvasDelegate {

	abstract void updateDelegate(Canvas canvas);

	abstract void drawColor(int color);

	abstract void drawLine(int startX, int startY, int stopX, int stopY,
			Paint paint);

	abstract void drawText(String text, int x, int y, Paint paint);

	abstract void drawRect(int left, int top, int right, int bottom, Paint paint);

	void drawImage(Bitmap bitmap, int left, int top, Paint paint);

	public abstract float transformY(double y);

	public abstract float transformX(double x);

}