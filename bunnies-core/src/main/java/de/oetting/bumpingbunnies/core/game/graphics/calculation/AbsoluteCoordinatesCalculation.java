package de.oetting.bumpingbunnies.core.game.graphics.calculation;

import de.oetting.bumpingbunnies.model.game.world.WorldProperties;

public class AbsoluteCoordinatesCalculation implements CoordinatesCalculation {

	private int width;
	private int height;
	private final WorldProperties properties;

	public AbsoluteCoordinatesCalculation(int width, int height, WorldProperties properties) {
		this.width = width;
		this.height = height;
		this.properties = properties;
	}

	@Override
	public int getGameCoordinateX(float touchX) {
		if (width == 0)
			return 0;
		return (int) (this.properties.getWorldWidth() / this.width * touchX);
	}

	@Override
	public int getGameCoordinateY(float touchY) {
		if (height == 0)
			return 0;
		return (int) (this.properties.getWorldHeight() - this.properties.getWorldHeight() / this.height * touchY);
	}

	@Override
	public int getScreenCoordinateX(long gameX) {
		return (int) ((gameX * this.width) / this.properties.getWorldWidth());
	}

	@Override
	public int getScreenCoordinateY(long gameY) {
		return (int) (((this.properties.getWorldHeight() - gameY) * this.height) / this.properties.getWorldHeight());
	}

	@Override
	public void updateCanvas(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void setZoom(int zoom) {
	}

	@Override
	public boolean isClickOnUpperHalf(int yCoordinate) {
		return yCoordinate > 0.5 * this.height;
	}

	@Override
	public void fixCurrentLocation() {
	}

	@Override
	public void resetCurrentLocation() {
	}

	@Override
	public int getDifferenceInGameCoordinateX(float screenX1, float screenX2) {
		return getGameCoordinateX(screenX1) - getGameCoordinateX(screenX2);
	}

	@Override
	public int getDifferenceInGameCoordinateY(float screenY1, float screenY2) {
		return getGameCoordinateY(screenY1) - getGameCoordinateY(screenY2);
	}

}
