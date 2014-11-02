package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;

public class ResizeRightAction implements MouseAction {

	private final GameObject selectedObject;
	private final MyCanvas canvas;
	private final CoordinatesCalculation coordinatesCalculation;

	public ResizeRightAction(GameObject selectedObject, MyCanvas canvas, CoordinatesCalculation coordinatesCalculation) {
		super();
		this.selectedObject = selectedObject;
		this.canvas = canvas;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void newMousePosition(MouseEvent event) {
		int newRight = this.coordinatesCalculation.getGameCoordinateX(event.getX());
		if (newRight > this.selectedObject.minX()) {
			this.selectedObject.setMaxX(newRight);
		}
		this.canvas.repaint();
	}

	@Override
	public void rightMouseClick(MouseEvent event) {
	}
}
