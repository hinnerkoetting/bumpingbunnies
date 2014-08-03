package de.jumpnbump.usecases.viewer.Viewer.actions;

import java.awt.event.MouseEvent;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.Viewer.CoordinatesCalculation;
import de.jumpnbump.usecases.viewer.model.GameObject;

public class MoveAction implements MouseAction {

	private final MyCanvas canvas;
	private final CoordinatesCalculation coordinatesCalculation;

	public MoveAction(MyCanvas canvas, CoordinatesCalculation coordinatesCalculation) {
		super();
		this.canvas = canvas;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void newMousePosition(MouseEvent event) {
		GameObject selectedGameObject = this.canvas.getSelectedGameObject();
		if (selectedGameObject != null) {
			int gameX = this.coordinatesCalculation.translateToGameX(event.getX());
			int gameY = this.coordinatesCalculation.translateToGameY(event.getY(), this.canvas.getHeight());
			selectedGameObject.setCenterX(gameX);
			selectedGameObject.setCenterY(gameY);
			this.canvas.repaint();
		}
	}

}
