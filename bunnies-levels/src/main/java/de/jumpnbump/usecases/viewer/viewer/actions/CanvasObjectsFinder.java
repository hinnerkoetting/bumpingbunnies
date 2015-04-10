package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;
import java.util.Optional;

import de.jumpnbump.usecases.viewer.viewer.editingMode.SelectionModeProvider;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public class CanvasObjectsFinder {

	private final CoordinatesCalculation coordinatesCalculation;
	private final SelectionModeProvider provider;

	public CanvasObjectsFinder(SelectionModeProvider provider) {
		this.coordinatesCalculation = provider.createCoordinatesCalculation();
		this.provider = provider;
	}

	public Optional<? extends GameObjectWithImage> findClickedObject(MouseEvent event) {
		long gameX = this.coordinatesCalculation.getGameCoordinateX(event.getX());
		long gameY = this.coordinatesCalculation.getGameCoordinateY(event.getY());
		return findGameObject(gameX, gameY);
	}

	private Optional<GameObjectWithImage> findGameObject(long gameX, long gameY) {
		return this.provider.getAllDrawingObjects().stream().filter((object) -> isSelected(object, gameX, gameY)).findFirst();
	}

	private boolean isSelected(GameObjectWithImage go, long gameX, long gameY) {
		return go.minX() < gameX && go.maxX() > gameX && go.minY() < gameY && go.maxY() > gameY;
	}
}
