package de.oetting.bumpingbunnies.core.game.graphics.calculation;


public interface CoordinatesCalculation {

	int getGameCoordinateY(float touchY);

	int getGameCoordinateX(float touchX);

	int getScreenCoordinateY(long gameY);

	int getScreenCoordinateX(long gameX);

	void updateCanvas(int width, int height);

	void setZoom(int zoom);

	boolean isClickOnUpperHalf(int yCoordinate);

	void fixCurrentLocation();

	void resetCurrentLocation();
}
