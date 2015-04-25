package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.jumpnbump.usecases.viewer.viewer.editingMode.SelectionModeProvider;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public class MoveAction implements MouseAction {

	private final CoordinatesCalculation coordinatesCalculation;
	private final SelectionModeProvider provider;
	private int firstPixelX = -1;
	private int firstPixelY = -1;
	private Map<GameObject, Long> firstGameX = new HashMap<>();
	private Map<GameObject, Long> firstGameY = new HashMap<>();
	boolean firstClick = true;

	public MoveAction(SelectionModeProvider provider, CoordinatesCalculation coordinatesCalculation) {
		this.provider = provider;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void newMousePosition(MouseEvent event) {
		if (!firstClick) {
			List<? extends GameObject> objects = provider.getCurrentSelectedObjects();
			for (GameObject go: objects)
				moveObjectByDifference(event, go);
		} else {
			firstClick = false;
			saveFirstCoordinates(event);
		}
	}

	private void saveFirstCoordinates(MouseEvent event) {
		firstPixelX = event.getX();
		firstPixelY = event.getY();
		List<? extends GameObjectWithImage> objects = provider.getCurrentSelectedObjects();
		for (GameObjectWithImage go: objects ) {
			firstGameX.put(go, go.getCenterX());
			firstGameY.put(go, go.getCenterY());
		}
	}

	private void moveObjectByDifference(MouseEvent event, GameObject selectedGameObject) {
		long diffGameX = this.coordinatesCalculation.getDifferenceInGameCoordinateY(firstPixelX, event.getX());
		long diffGameY = this.coordinatesCalculation.getDifferenceInGameCoordinateY(firstPixelY, event.getY());
		selectedGameObject.setCenterX(firstGameX.get(selectedGameObject) + diffGameX);
		selectedGameObject.setCenterY(firstGameY.get(selectedGameObject) - diffGameY);
		provider.repaintCanvas();
	}

	@Override
	public void rightMouseClick(MouseEvent event) {
	}

}
