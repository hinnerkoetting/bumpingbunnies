package de.oetting.bumpingbunnies.usecases.game.graphics;

import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;

public class GameToAndroidTransformation {

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	public static float transformX(double x, int canvasWidth) {
		return (float) ((x * canvasWidth) / ModelConstants.MAX_VALUE);
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	public static float transformY(double y, int canvasHeight) {
		return (float) (((ModelConstants.MAX_VALUE - y) * canvasHeight) / ModelConstants.MAX_VALUE);
	}
}
