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

	public SelectAction(MyCanvas canvas, ObjectContainer container) {
		super();
		this.canvas = canvas;
		this.container = container;
	}

	@Override
	public void newMousePosition(MouseEvent e) {
		long gameX = CoordinatesCalculation.translateToGameX((int) (e.getX() * this.canvas.getZoom()));
		long gameY = CoordinatesCalculation.translateToGameY((int) (e.getY() * this.canvas.getZoom()), this.canvas.getHeight());
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
