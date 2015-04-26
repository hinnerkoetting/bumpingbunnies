package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;

import de.jumpnbump.usecases.viewer.viewer.editingMode.SelectionModeProvider;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;

public class ResizeTopMouseAction implements MouseAction {

	private final GameObject selectedObject;
	private final CoordinatesCalculation coordinatesCalculation;
	private final SelectionModeProvider provider;

	public ResizeTopMouseAction(GameObject selectedObject, SelectionModeProvider provider, CoordinatesCalculation coordinatesCalculation) {
		super();
		this.selectedObject = selectedObject;
		this.provider = provider;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void onMouseDragged(MouseEvent event) {
		int newTopY = this.coordinatesCalculation.getGameCoordinateY(event.getY());
		if (newTopY > this.selectedObject.minY()) {
			this.selectedObject.setMaxY(newTopY);
		}
		provider.repaintCanvas();
	}

	@Override
	public void rightMouseClick(MouseEvent event) {
	}
}
