package de.oetting.bumpingbunnies.core.game.graphics.calculation;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;

public class CoordinatesCalculationFactory {

	public static RelativeCoordinatesCalculation createCoordinatesCalculation(CameraPositionCalculation cameraPositionCalculation) {
		return new RelativeCoordinatesCalculation(cameraPositionCalculation);
	}

}
