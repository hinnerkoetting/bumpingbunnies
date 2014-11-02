package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;
import java.util.Optional;

import de.jumpnbump.usecases.viewer.viewer.editingMode.SelectionModeProvider;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;

public class MoveAction implements MouseAction {

	private final CoordinatesCalculation coordinatesCalculation;
	private final SelectionModeProvider provider;

	public MoveAction(SelectionModeProvider provider, CoordinatesCalculation coordinatesCalculation) {
		this.provider = provider;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void newMousePosition(MouseEvent event) {
		Optional<? extends GameObject> object = provider.getCurrentSelectedObject();
		if (object.isPresent()) {
			GameObject selectedGameObject = object.get();
			long gameX = this.coordinatesCalculation.getGameCoordinateX(event.getX());
			long gameY = this.coordinatesCalculation.getGameCoordinateY(event.getY());
			selectedGameObject.setCenterX(gameX);
			selectedGameObject.setCenterY(gameY);
			provider.repaintCanvas();
		}
	}

	@Override
	public void rightMouseClick(MouseEvent event) {
	}

}
