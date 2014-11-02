package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;

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
			long gameX = this.coordinatesCalculation.getGameCoordinateX(event.getX());
			long gameY = this.coordinatesCalculation.getGameCoordinateY(event.getY());
			selectedGameObject.setCenterX(gameX);
			selectedGameObject.setCenterY(gameY);
			this.canvas.repaint();
		}
	}

	@Override
	public void rightMouseClick(MouseEvent event) {
	}

}
