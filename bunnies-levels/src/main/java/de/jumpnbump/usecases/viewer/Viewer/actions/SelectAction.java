package de.jumpnbump.usecases.viewer.Viewer.actions;

import java.awt.event.MouseEvent;
import java.util.List;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public class SelectAction implements MouseAction {

	private final MyCanvas canvas;
	private final World container;
	private final CoordinatesCalculation coordinatesCalculation;

	public SelectAction(MyCanvas canvas, World container, CoordinatesCalculation coordinatesCalculation) {
		super();
		this.canvas = canvas;
		this.container = container;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void newMousePosition(MouseEvent e) {
		long gameX = this.coordinatesCalculation.getGameCoordinateX((e.getX()));
		long gameY = this.coordinatesCalculation.getGameCoordinateY((e.getY()));
		GameObject go = findGameObject(gameX, gameY);
		this.canvas.setSelectedObject(go);
		this.canvas.repaint();
		this.canvas.setSelectedObject(go);
	}

	private GameObject findGameObject(long gameX, long gameY) {
		List<GameObjectWithImage> allObjects = this.container.getAllObjects();
		for (GameObject go : allObjects) {
			if (go.minX() < gameX && go.maxX() > gameX && go.minY() < gameY && go.maxY() > gameY) {
				return go;
			}
		}
		return null;
	}
}
