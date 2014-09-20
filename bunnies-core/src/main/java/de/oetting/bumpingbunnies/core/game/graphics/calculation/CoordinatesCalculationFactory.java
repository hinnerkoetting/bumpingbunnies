package de.oetting.bumpingbunnies.core.game.graphics.calculation;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class CoordinatesCalculationFactory {

	public static RelativeCoordinatesCalculation createCoordinatesCalculation(Player player) {
		return createCoordinatesCalculation(createCameraPositionCalculator(player));
	}

	public static RelativeCoordinatesCalculation createCoordinatesCalculation(CameraPositionCalculation cameraPositionCalculation) {
		return new RelativeCoordinatesCalculation(cameraPositionCalculation);
	}

	public static CameraPositionCalculation createCameraPositionCalculator(Player player) {
		return new CameraPositionCalculation(player);
	}
}
