package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;

import de.jumpnbump.usecases.viewer.viewer.editingMode.SelectionModeProvider;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;

public class ResizeLeftAction implements MouseAction {

	private final GameObject selectedObject;
	private final CoordinatesCalculation coordinatesCalculation;
	private final SelectionModeProvider provider;

	public ResizeLeftAction(GameObject selectedObject, SelectionModeProvider provider, CoordinatesCalculation coordinatesCalculation) {
		this.selectedObject = selectedObject;
		this.provider = provider;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void onMouseDragged(MouseEvent event) {
		int newLeft = this.coordinatesCalculation.getGameCoordinateX(event.getX());
		if (newLeft < this.selectedObject.maxX()) {
			this.selectedObject.setMinX(newLeft);
		}
		provider.repaintCanvas();
	}

	@Override
	public void rightMouseClick(MouseEvent event) {
	}
}
