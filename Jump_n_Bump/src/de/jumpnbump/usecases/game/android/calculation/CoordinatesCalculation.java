package de.jumpnbump.usecases.game.android.calculation;

public interface CoordinatesCalculation {

	int getGameCoordinateY(float touchY);

	int getGameCoordinateX(float touchX);

	float getScreenCoordinateY(int gameY);

	float getScreenCoordinateX(int gameX);
}
