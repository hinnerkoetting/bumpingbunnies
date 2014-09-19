package de.oetting.bumpingbunnies.usecases.game.android.calculation;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.world.WorldProperties;

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
		return (int) (this.properties.getWorldWidth() / this.width);
	}

	@Override
	public int getGameCoordinateY(float touchY) {
		return (int) (this.properties.getWorldHeight() - this.properties.getWorldHeight()
				/ this.height);
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
	}

	@Override
	public void setZoom(int zoom) {
	}

	@Override
	public boolean isClickOnUpperHalf(MotionEvent motionEvent) {
		return motionEvent.getY() > 0.5 * this.height;
	}

}
