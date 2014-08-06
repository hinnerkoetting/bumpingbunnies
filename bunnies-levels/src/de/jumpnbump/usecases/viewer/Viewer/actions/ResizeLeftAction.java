package de.jumpnbump.usecases.viewer.Viewer.actions;

import java.awt.event.MouseEvent;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.Viewer.CoordinatesCalculation;
import de.jumpnbump.usecases.viewer.model.GameObject;

public class ResizeLeftAction implements MouseAction {

	private final GameObject selectedObject;
	private final MyCanvas canvas;
	private final CoordinatesCalculation coordinatesCalculation;

	public ResizeLeftAction(GameObject selectedObject, MyCanvas canvas, CoordinatesCalculation coordinatesCalculation) {
		super();
		this.selectedObject = selectedObject;
		this.canvas = canvas;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void newMousePosition(MouseEvent event) {
		int newLeft = this.coordinatesCalculation.translateToGameX(event.getX());
		if (newLeft < this.selectedObject.maxX()) {
			this.selectedObject.setMinX(newLeft);
		}
		this.canvas.repaint();
	}
}
