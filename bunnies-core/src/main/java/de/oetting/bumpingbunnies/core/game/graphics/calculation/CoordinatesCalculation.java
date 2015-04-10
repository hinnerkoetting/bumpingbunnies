package de.oetting.bumpingbunnies.core.game.graphics.calculation;

public interface CoordinatesCalculation {

	int getGameCoordinateY(float screenY);

	int getGameCoordinateX(float screenX);

	int getDifferenceInGameCoordinateX(float screenX1, float screenX2);
	
	int getDifferenceInGameCoordinateY(float screenY1, float screenY2);

	int getScreenCoordinateY(long gameY);

	int getScreenCoordinateX(long gameX);

	void updateCanvas(int width, int height);

	void setZoom(int zoom);

	boolean isClickOnUpperHalf(int yCoordinate);

	void fixCurrentLocation();

	void resetCurrentLocation();
}
