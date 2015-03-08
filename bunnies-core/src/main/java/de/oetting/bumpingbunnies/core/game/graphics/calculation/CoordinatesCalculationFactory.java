package de.oetting.bumpingbunnies.core.game.graphics.calculation;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;

public class CoordinatesCalculationFactory {

	public static RelativeCoordinatesCalculation createCoordinatesCalculation(CameraPositionCalculation cameraPositionCalculation, WorldProperties properties) {
		return new RelativeCoordinatesCalculation(cameraPositionCalculation, properties);
	}

}
