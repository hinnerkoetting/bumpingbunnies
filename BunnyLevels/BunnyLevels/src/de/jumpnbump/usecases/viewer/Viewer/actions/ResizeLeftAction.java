package de.jumpnbump.usecases.viewer.Viewer.actions;

import java.awt.event.MouseEvent;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.Viewer.CoordinatesCalculation;
import de.jumpnbump.usecases.viewer.model.GameObject;

public class ResizeLeftAction implements MouseAction {

	private final GameObject selectedObject;
	private final MyCanvas canvas;

	public ResizeLeftAction(GameObject selectedObject, MyCanvas canvas) {
		super();
		this.selectedObject = selectedObject;
		this.canvas = canvas;
	}

	@Override
	public void newMousePosition(MouseEvent event) {
		int newLeft = CoordinatesCalculation.translateToGameX(event.getX() * this.canvas.getZoom());
		if (newLeft < this.selectedObject.maxX()) {
			this.selectedObject.setMinX(newLeft);
		}
		this.canvas.repaint();
	}
}
