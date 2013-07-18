package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.model.GameObject;
import de.jumpnbump.usecases.viewer.xml.ObjectContainer;

public class CanvasMouseListener implements MouseListener, MouseMotionListener {

	private final ObjectContainer container;
	private final MyCanvas canvas;
	private GameObject selectedObject;

	public CanvasMouseListener(ObjectContainer container, MyCanvas canvas) {
		super();
		this.container = container;
		this.canvas = canvas;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	private void selectItem(MouseEvent e) {
		long gameX = translateToGameX(e.getX());
		long gameY = translateToGameY(e.getY());
		GameObject go = findGameObject(gameX, gameY);
		this.canvas.setSelectedObject(go);
		this.canvas.repaint();
		this.selectedObject = go;
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

	private int translateToGameX(int pixelX) {
		return pixelX * MyCanvas.DIVIDER_X_CONST;
	}

	private int translateToGameY(int pixelY) {
		return (int) ((this.canvas.getHeight() * 0.9 - pixelY) * MyCanvas.DIVIDER_Y_CONST);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		selectItem(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.selectedObject = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.selectedObject != null) {
			int gameX = translateToGameX(e.getX());
			int gameY = translateToGameY(e.getY());
			this.selectedObject.setCenterX(gameX);
			this.selectedObject.setCenterY(gameY);
			this.canvas.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
