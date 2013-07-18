package de.jumpnbump.usecases.viewer.Viewer.actions;

import java.awt.event.MouseEvent;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.Viewer.CoordinatesCalculation;
import de.jumpnbump.usecases.viewer.model.GameObject;

public class ResizeRightAction implements MouseAction {

	private final GameObject selectedObject;
	private final MyCanvas canvas;

	public ResizeRightAction(GameObject selectedObject, MyCanvas canvas) {
		super();
		this.selectedObject = selectedObject;
		this.canvas = canvas;
	}

	@Override
	public void newMousePosition(MouseEvent event) {
		int newRight = CoordinatesCalculation.translateToGameX(event.getX());
		if (newRight > this.selectedObject.minX()) {
			this.selectedObject.setMaxX(newRight);
		}
		this.canvas.repaint();
	}
}
