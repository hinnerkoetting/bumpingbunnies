package de.oetting.bumpingbunnies.pc.graphics;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;

public class YCoordinateInverterCalculation implements CoordinatesCalculation {

	private final CoordinatesCalculation delegate;
	private int screenHeight;

	public YCoordinateInverterCalculation(CoordinatesCalculation delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public int getGameCoordinateY(float touchY) {
		return delegate.getGameCoordinateY(screenHeight - touchY);
	}

	@Override
	public int getGameCoordinateX(float touchX) {
		return delegate.getGameCoordinateX(touchX);
	}

	@Override
	public int getScreenCoordinateY(long gameY) {
		return delegate.getScreenCoordinateY(gameY);
	}

	@Override
	public int getScreenCoordinateX(long gameX) {
		return delegate.getScreenCoordinateX(gameX);
	}

	@Override
	public void updateCanvas(int width, int height) {
		delegate.updateCanvas(width, height);
		screenHeight = height;
	}

	@Override
	public void setZoom(int zoom) {
		delegate.setZoom(zoom);
	}

	@Override
	public boolean isClickOnUpperHalf(int yCoordinate) {
		return delegate.isClickOnUpperHalf(screenHeight - yCoordinate);
	}

	@Override
	public void fixCurrentLocation() {
		delegate.fixCurrentLocation();
	}

	@Override
	public void resetCurrentLocation() {
		delegate.resetCurrentLocation();		
	}

	@Override
	public int getDifferenceInGameCoordinateX(float screenX1, float screenX2) {
		return delegate.getDifferenceInGameCoordinateX(screenX1, screenX2);
	}

	@Override
	public int getDifferenceInGameCoordinateY(float screenY1, float screenY2) {
		return delegate.getDifferenceInGameCoordinateY(screenY1, screenY2);
	}

}
