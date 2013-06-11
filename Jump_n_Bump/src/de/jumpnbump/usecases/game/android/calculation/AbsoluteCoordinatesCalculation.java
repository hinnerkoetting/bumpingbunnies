package de.jumpnbump.usecases.game.android.calculation;

import de.jumpnbump.usecases.game.graphics.GameToAndroidTransformation;
import de.jumpnbump.usecases.game.model.ModelConstants;

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
	public float getScreenCoordinateX(int gameX) {
		return GameToAndroidTransformation.transformX(gameX, this.width);
	}

	@Override
	public float getScreenCoordinateY(int gameY) {
		return GameToAndroidTransformation.transformY(gameY, this.height);
	}

}
