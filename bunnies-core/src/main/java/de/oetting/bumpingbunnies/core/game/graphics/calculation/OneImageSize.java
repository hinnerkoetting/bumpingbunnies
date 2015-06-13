package de.oetting.bumpingbunnies.core.game.graphics.calculation;

import de.oetting.bumpingbunnies.core.game.graphics.CanvasAdapter;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;

public class OneImageSize {

	public static int computeWidthForOneImage(CoordinatesCalculation coordinatesCalculation, CanvasAdapter screenCanvas) {
		int completeGameWidth = gameWidthInPixels(coordinatesCalculation);
		return completeGameWidth;
//		return Math.max(completeGameWidth, screenCanvas.getOriginalWidth());
	}

	public static int gameWidthInPixels(CoordinatesCalculation coordinatesCalculation) {
		return coordinatesCalculation.getScreenCoordinateX(ModelConstants.STANDARD_WORLD_SIZE)
				- coordinatesCalculation.getScreenCoordinateX(0);
	}

	public static int computeHeightForOneImage(CoordinatesCalculation coordinatesCalculation, CanvasAdapter screenCanvas) {
		return screenheightInPixels(coordinatesCalculation);
//		return Math.max(
//				screenheightInPixels(coordinatesCalculation),
//				screenCanvas.getOriginalHeight());
	}

	public static int screenheightInPixels(CoordinatesCalculation coordinatesCalculation) {
		return coordinatesCalculation.getScreenCoordinateY(0)
				- coordinatesCalculation.getScreenCoordinateY(ModelConstants.STANDARD_WORLD_SIZE);
	}
}
