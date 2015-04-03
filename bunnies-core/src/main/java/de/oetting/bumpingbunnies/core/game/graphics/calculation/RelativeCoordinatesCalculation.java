package de.oetting.bumpingbunnies.core.game.graphics.calculation;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;

public class RelativeCoordinatesCalculation implements CoordinatesCalculation {

	private final WorldProperties properties;
	private final CameraPositionCalculation cameraPositionCalculation;
	private int zoom;
	private int width;
	private int height;
	//This will avoid flickering on the screen.
	//otherwise the player might move while objects are drawn.
	//we set this fields before any object is drawn and reset it after all objects are drawn.
	private long currentGameCenterX;
	private long currentGameCenterY;

	public RelativeCoordinatesCalculation(CameraPositionCalculation cameraPositionCalculation,
			WorldProperties properties) {
		this.cameraPositionCalculation = cameraPositionCalculation;
		this.properties = properties;
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
	public int getGameCoordinateX(float displayX) {
		return (int) (this.zoom * displayX + getGameCenterX() - this.width / 2 * this.zoom);
	}

	@Override
	public int getGameCoordinateY(float displayX) {
		return (int) (-(displayX - this.height / 2) * this.zoom + getGameCenterY());
	}

	@Override
	public int getScreenCoordinateX(long gameX) {
		return getScreenCoordinateX(gameX, currentGameCenterX);
	}

	@Override
	public int getScreenCoordinateY(long gameY) {
		return getScreenCoordinateY(gameY, currentGameCenterY);
	}

	public int getScreenCoordinateY(long gameY, long gameCenterY) {
		if (gameCenterY == Integer.MIN_VALUE) 
			throw new IllegalStateException("Need so fix current location before drawing");
		return (int) (this.height / 2 - ((+gameY - gameCenterY)) / this.zoom);
	}

	@Override
	public boolean isClickOnUpperHalf(int yCoordinate) {
		return getGameCoordinateY(yCoordinate) > getGameCenterY();
	}

	public long getGameCenterX() {
		if (canSeeBehindBothBordersFromCenter())
			return properties.getWorldWidth() / 2;
		long centerFromCalculation = this.cameraPositionCalculation.getCurrentScreenX();
		if (cameraSeesBehindLeftBorder(centerFromCalculation))
			return minimalXPosition();
		if (cameraSeesBehindRightBorder(centerFromCalculation))
			return maximalXPosition();
		return centerFromCalculation;
	}

	private boolean canSeeBehindBothBordersFromCenter() {
		return cameraSeesBehindLeftBorder(properties.getWorldWidth() / 2)
				&& cameraSeesBehindRightBorder(properties.getWorldWidth() / 2);
	}

	private boolean cameraSeesBehindLeftBorder(long centerFromCalculation) {
		return getScreenCoordinateX(-1, centerFromCalculation) > 0;
	}

	private boolean cameraSeesBehindRightBorder(long centerFromCalculation) {
		return getScreenCoordinateX(properties.getWorldWidth() + 1, centerFromCalculation) < width;
	}

	private int getScreenCoordinateX(long gameX, long gameCenterX) {
		if (gameCenterX == Integer.MIN_VALUE) 
			throw new IllegalStateException("Need so fix current location before drawing");
		return (int) (this.width / 2 + (gameX - gameCenterX) / this.zoom);
	}

	private long minimalXPosition() {
		return width * zoom / 2;
	}

	private long maximalXPosition() {
		return properties.getWorldWidth() - (width / 2) * zoom;
	}

	public long getGameCenterY() {
		if (canSeeBehindTopAndBottomFromCenter())
			return properties.getWorldHeight() / 2;
		long centerFromCalculation = this.cameraPositionCalculation.getCurrentScreenY();
		if (cameraSeesBehindTopBorder(centerFromCalculation))
			return minimalYPosition();
		if (cameraSeesBehindBottomBorder(centerFromCalculation))
			return maximalYPosition();
		return centerFromCalculation;
	}

	private boolean canSeeBehindTopAndBottomFromCenter() {
		return cameraSeesBehindTopBorder(properties.getWorldHeight() / 2)
				&& cameraSeesBehindBottomBorder(properties.getWorldHeight() / 2);
	}

	private boolean cameraSeesBehindBottomBorder(long gameCenterY) {
		return getScreenCoordinateY(-1, gameCenterY) < height;
	}

	private boolean cameraSeesBehindTopBorder(long gameCenterY) {
		return getScreenCoordinateY(properties.getWorldHeight() + 1, gameCenterY) > 0;
	}

	private long minimalYPosition() {
		return properties.getWorldHeight() - (height / 2) * zoom;
	}

	private long maximalYPosition() {
		return height * zoom / 2;
	}

	@Override
	public void fixCurrentLocation() {
		currentGameCenterX = getGameCenterX();
		currentGameCenterY = getGameCenterY();
	}

	@Override
	public void resetCurrentLocation() {
		currentGameCenterX = Integer.MIN_VALUE;
		currentGameCenterY = Integer.MIN_VALUE;
	}

}
