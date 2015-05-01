package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;

import de.jumpnbump.usecases.viewer.viewer.editingMode.SelectionModeProvider;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;

public class ResizeDownAction implements MouseAction {

	private final GameObject selectedObject;
	private final CoordinatesCalculation coordinatesCalculation;
	private final SelectionModeProvider provider;

	public ResizeDownAction(GameObject selectedObject, SelectionModeProvider provider, CoordinatesCalculation coordinatesCalculation) {
		super();
		this.selectedObject = selectedObject;
		this.provider = provider;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void onMouseDragged(MouseEvent event) {
		int newBottomY = this.coordinatesCalculation.getGameCoordinateY(event.getY());
		if (newBottomY < this.selectedObject.maxY()) {
			this.selectedObject.setMinY(newBottomY);
		}
		provider.repaintCanvas();
	}

	@Override
	public void rightMouseClick(MouseEvent event) {
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		provider.storeCurrentState();
	}
}
