package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;
import java.util.Optional;

import de.jumpnbump.usecases.viewer.viewer.editingMode.SelectionModeProvider;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.world.WorldConstants;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;

public class MoveAction implements MouseAction {

	private final CoordinatesCalculation coordinatesCalculation;
	private final SelectionModeProvider provider;
	private int firstPixelX = -1;
	private int firstPixelY = -1;
	private long firstGameX = -1;
	private long firstGameY = -1;
	boolean first = true;

	public MoveAction(SelectionModeProvider provider, CoordinatesCalculation coordinatesCalculation) {
		this.provider = provider;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void newMousePosition(MouseEvent event) {
		if (!first) {
			Optional<? extends GameObject> object = provider.getCurrentSelectedObject();
			if (object.isPresent()) {
				moveObjectByDifference(event, object);
			}
		} else {
			first = false;
			saveFirstCoordinates(event);
		}
	}

	private void saveFirstCoordinates(MouseEvent event) {
		firstPixelX = event.getX();
		firstPixelY = event.getY();
		GameObjectWithImage go = provider.getCurrentSelectedObject().get();
		firstGameX = go.getCenterX();
		firstGameY = go.getCenterY();
	}

	private void moveObjectByDifference(MouseEvent event, Optional<? extends GameObject> object) {
		GameObject selectedGameObject = object.get();
		long diffGameX = this.coordinatesCalculation.getDifferenceInGameCoordinateY(firstPixelX, event.getX());
		long diffGameY = this.coordinatesCalculation.getDifferenceInGameCoordinateY(firstPixelY, event.getY());
		selectedGameObject.setCenterX(firstGameX + diffGameX);
		selectedGameObject.setCenterY(firstGameY - diffGameY);
		provider.repaintCanvas();
	}

	@Override
	public void rightMouseClick(MouseEvent event) {
	}

}
