package de.oetting.bumpingbunnies.usecases.game.android.calculation;

import android.view.MotionEvent;

public interface CoordinatesCalculation {

	int getGameCoordinateY(float touchY);

	int getGameCoordinateX(float touchX);

	int getScreenCoordinateY(long gameY);

	int getScreenCoordinateX(long gameX);

	void updateCanvas(int width, int height);

	void setZoom(int zoom);

	boolean isClickOnUpperHalf(MotionEvent motionEvent);
}
