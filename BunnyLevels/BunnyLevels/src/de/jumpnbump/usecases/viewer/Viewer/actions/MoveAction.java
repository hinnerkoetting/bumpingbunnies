package de.jumpnbump.usecases.viewer.Viewer.actions;

import java.awt.event.MouseEvent;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.Viewer.CoordinatesCalculation;
import de.jumpnbump.usecases.viewer.model.GameObject;

public class MoveAction implements MouseAction {

	private final MyCanvas canvas;

	public MoveAction(MyCanvas canvas) {
		super();
		this.canvas = canvas;
	}

	@Override
	public void newMousePosition(MouseEvent event) {
		GameObject selectedGameObject = this.canvas.getSelectedGameObject();
		if (selectedGameObject != null) {
			int gameX = CoordinatesCalculation.translateToGameX(event.getX() * this.canvas.getZoom());
			int gameY = CoordinatesCalculation.translateToGameY(event.getY() * this.canvas.getZoom(), this.canvas.getHeight());
			selectedGameObject.setCenterX(gameX);
			selectedGameObject.setCenterY(gameY);
			this.canvas.repaint();
		}
	}

}
