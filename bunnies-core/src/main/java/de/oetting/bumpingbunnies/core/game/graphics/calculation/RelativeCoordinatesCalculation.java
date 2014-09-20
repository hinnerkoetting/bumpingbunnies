package de.oetting.bumpingbunnies.core.game.graphics.calculation;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;

public class RelativeCoordinatesCalculation implements CoordinatesCalculation {

	private CameraPositionCalculation cameraPositionCalculation;
	private int zoom;
	private int width;
	private int height;

	public RelativeCoordinatesCalculation(CameraPositionCalculation cameraPositionCalculation) {
		this.cameraPositionCalculation = cameraPositionCalculation;
	}

	@Override
	public void updateCanvas(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	@Override
	public int getGameCoordinateX(float touchX) {
		return (int) (this.zoom * touchX + (getCurrentCenterX() - this.width / 2 * this.zoom));
	}

	@Override
	public int getGameCoordinateY(float touchY) {
		return (int) (-(touchY - this.height / 2) * this.zoom + getCurrentCenterY());
	}

	@Override
	public int getScreenCoordinateX(long gameX) {
		int res = (int) (this.width / 2 + (gameX - getCurrentCenterX()) / this.zoom);
		return res;
	}

	@Override
	public int getScreenCoordinateY(long gameY) {
		int res = (int) (this.height / 2 - (((+gameY - getCurrentCenterY())) / this.zoom));
		return res;
	}

	@Override
	public boolean isClickOnUpperHalf(int yCoordinate) {
		return getGameCoordinateY(yCoordinate) > getCurrentCenterY();
	}

	public long getCurrentCenterX() {
		return this.cameraPositionCalculation.getCurrentScreenX();
	}

	public long getCurrentCenterY() {
		return this.cameraPositionCalculation.getCurrentScreenY();
	}

}
