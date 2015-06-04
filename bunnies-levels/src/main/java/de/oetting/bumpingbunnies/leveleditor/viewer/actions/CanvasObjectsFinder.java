package de.oetting.bumpingbunnies.leveleditor.viewer.actions;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.oetting.bumpingbunnies.core.game.graphics.NegativeZIndexComparator;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.SingleCollisionDetection;
import de.oetting.bumpingbunnies.leveleditor.viewer.editingMode.SelectionModeProvider;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.Rectangle;
import de.oetting.bumpingbunnies.model.game.world.ZIndexComparator;

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
	
	public Optional<? extends GameObjectWithImage> findClickedObject(long gameX, long gameY) {
		return findGameObject(gameX, gameY);
	}

	private Optional<GameObjectWithImage> findGameObject(long gameX, long gameY) {
		return this.provider.getAllDrawingObjects().stream().filter((object) -> isSelected(object, gameX, gameY)).sorted(new NegativeZIndexComparator()).findFirst();
	}

	private boolean isSelected(GameObjectWithImage go, long gameX, long gameY) {
		return go.minX() < gameX && go.maxX() > gameX && go.minY() < gameY && go.maxY() > gameY;
	}

	public List<GameObjectWithImage> findAllSelectedObjects(Rectangle rectangle) {
		return provider.getAllDrawingObjects().stream().filter(object -> isSelected(object, rectangle)).collect(Collectors.toList());
	}

	private boolean isSelected(GameObjectWithImage object, Rectangle rectangle) {
		return SingleCollisionDetection.collides(object, rectangle);
	}
}
