package de.oetting.bumpingbunnies.pc.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;

public class CanvasCoordinatesTranslation {

	private final CoordinatesCalculation coordinatesCalculation;
	private final GraphicsContext graphicsContext;

	public CanvasCoordinatesTranslation(CoordinatesCalculation coordinatesCalculation, GraphicsContext graphicsContext) {
		super();
		this.coordinatesCalculation = coordinatesCalculation;
		this.graphicsContext = graphicsContext;
	}

	public void drawRect(long left, long top, long right, long bottom, Paint paint) {
		graphicsContext.setFill(paint);
		graphicsContext.fillRect(transformX(left), transformY(top), transformX(right - left), transformY(bottom - top));
	}

	private double transformX(long gameX) {
		return coordinatesCalculation.getScreenCoordinateX(gameX);
	}

	private double transformY(long gameY) {
		return coordinatesCalculation.getScreenCoordinateY(gameY);
	}
}
