package de.jumpnbump.usecases.viewer.Viewer.actions;

import java.awt.event.MouseEvent;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.Viewer.CoordinatesCalculation;
import de.jumpnbump.usecases.viewer.model.GameObject;

public class ResizeTopMouseAction implements MouseAction {

	private final GameObject selectedObject;
	private final MyCanvas canvas;

	public ResizeTopMouseAction(GameObject selectedObject, MyCanvas canvas) {
		super();
		this.selectedObject = selectedObject;
		this.canvas = canvas;
	}

	@Override
	public void newMousePosition(MouseEvent event) {
		int newTopY = CoordinatesCalculation.translateToGameY(event.getY() * this.canvas.getZoom(), this.canvas.getHeight());
		if (newTopY > this.selectedObject.minY()) {
			this.selectedObject.setMaxY(newTopY);
		}
		this.canvas.repaint();
	}
}
