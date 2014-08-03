package de.jumpnbump.usecases.viewer.Viewer.actions;

import java.awt.event.MouseEvent;
import java.util.List;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.Viewer.CoordinatesCalculation;
import de.jumpnbump.usecases.viewer.model.GameObject;
import de.jumpnbump.usecases.viewer.xml.ObjectContainer;

public class SelectAction implements MouseAction {

	private final MyCanvas canvas;
	private final ObjectContainer container;
	private final CoordinatesCalculation coordinatesCalculation;

	public SelectAction(MyCanvas canvas, ObjectContainer container, CoordinatesCalculation coordinatesCalculation) {
		super();
		this.canvas = canvas;
		this.container = container;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void newMousePosition(MouseEvent e) {
		long gameX = this.coordinatesCalculation.translateToGameX((e.getX()));
		long gameY = this.coordinatesCalculation.translateToGameY((e.getY()), this.canvas.getHeight());
		GameObject go = findGameObject(gameX, gameY);
		this.canvas.setSelectedObject(go);
		this.canvas.repaint();
		this.canvas.setSelectedObject(go);
	}

	private GameObject findGameObject(long gameX, long gameY) {
		List<GameObject> allObjects = this.container.allObjects();
		for (GameObject go : allObjects) {
			if (go.minX() < gameX && go.maxX() > gameX && go.minY() < gameY && go.maxY() > gameY) {
				return go;
			}
		}
		return null;
	}
}
