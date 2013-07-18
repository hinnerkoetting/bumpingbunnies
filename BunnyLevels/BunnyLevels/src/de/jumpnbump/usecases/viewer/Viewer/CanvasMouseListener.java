package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.Cursor;
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

	public CanvasMouseListener(ObjectContainer container, MyCanvas canvas) {
		super();
		this.container = container;
		this.canvas = canvas;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	private void selectItem(MouseEvent e) {
		long gameX = CoordinatesCalculation.translateToGameX(e.getX());
		long gameY = CoordinatesCalculation.translateToGameY(e.getY(), this.canvas.getHeight());
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

	@Override
	public void mousePressed(MouseEvent e) {
		selectItem(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		GameObject selectedGameObject = this.canvas.getSelectedGameObject();
		if (selectedGameObject != null) {
			int gameX = translateToGameX(e.getX());
			int gameY = translateToGameY(e.getY());
			selectedGameObject.setCenterX(gameX);
			selectedGameObject.setCenterY(gameY);
			this.canvas.repaint();
		}
	}

	private int translateToGameY(int y) {
		return CoordinatesCalculation.translateToGameY(y, this.canvas.getHeight());
	}

	private int translateToGameX(int x) {
		return CoordinatesCalculation.translateToGameX(x);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		GameObject selectedGameObject = this.canvas.getSelectedGameObject();
		if (selectedGameObject != null) {
			int pixelMinX = selectedGameObject.minX();
			int pixelMaxX = selectedGameObject.maxX();
			int pixelMinY = selectedGameObject.minY();
			int pixelMaxY = selectedGameObject.maxY();
			this.canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			if (Math.abs(e.getX() - translateToPixelX(pixelMinX)) < 10) {
				this.canvas.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
			}
			if (Math.abs(e.getX() - translateToPixelX(pixelMaxX)) < 10) {
				this.canvas.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
			}
			if (Math.abs(e.getY() - translateToPixelY(pixelMinY)) < 10) {
				this.canvas.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			}
			if (Math.abs(e.getY() - translateToPixelY(pixelMaxY)) < 10) {
				this.canvas.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			}

		}
	}

	private int translateToPixelX(int gameX) {
		return CoordinatesCalculation.calculatePixelX(gameX);
	}

	private int translateToPixelY(int gameY) {
		return CoordinatesCalculation.calculatePixelY(gameY, this.canvas.getHeight());
	}

}
