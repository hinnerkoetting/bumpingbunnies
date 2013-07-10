package de.oetting.bumpingbunnies.usecases.game.android.calculation;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.usecases.game.graphics.GameToAndroidTransformation;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;

public class AbsoluteCoordinatesCalculation implements CoordinatesCalculation {

	private int width;
	private int height;

	public AbsoluteCoordinatesCalculation(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public int getGameCoordinateX(float touchX) {
		return ModelConstants.MAX_VALUE / this.width;
	}

	@Override
	public int getGameCoordinateY(float touchY) {
		return ModelConstants.MAX_VALUE - ModelConstants.MAX_VALUE
				/ this.height;
	}

	@Override
	public float getScreenCoordinateX(long gameX) {
		return GameToAndroidTransformation.transformX(gameX, this.width);
	}

	@Override
	public float getScreenCoordinateY(long gameY) {
		return GameToAndroidTransformation.transformY(gameY, this.height);
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
