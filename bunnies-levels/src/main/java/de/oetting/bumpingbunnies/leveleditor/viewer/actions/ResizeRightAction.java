package de.oetting.bumpingbunnies.leveleditor.viewer.actions;

import java.awt.event.MouseEvent;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.leveleditor.viewer.editingMode.SelectionModeProvider;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;

public class ResizeRightAction implements MouseAction {

	private final GameObject selectedObject;
	private final CoordinatesCalculation coordinatesCalculation;
	private final SelectionModeProvider provider;

	public ResizeRightAction(GameObject selectedObject, SelectionModeProvider provider,
			CoordinatesCalculation coordinatesCalculation) {
		this.selectedObject = selectedObject;
		this.provider = provider;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void onMouseDragged(MouseEvent event) {
		int newRight = this.coordinatesCalculation.getGameCoordinateX(event.getX());
		if (newRight > this.selectedObject.minX()) {
			this.selectedObject.setMaxX(newRight);
		}
		provider.refreshView();
	}

	@Override
	public void onMousePressedFirst(MouseEvent event) {
		provider.storeCurrentState();
		MouseAction.super.onMousePressedFirst(event);
	}

	@Override
	public void rightMouseClick(MouseEvent event) {
	}

}
