package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.Viewer.actions.MouseAction;
import de.jumpnbump.usecases.viewer.Viewer.actions.MoveAction;
import de.jumpnbump.usecases.viewer.Viewer.actions.ResizeDownAction;
import de.jumpnbump.usecases.viewer.Viewer.actions.ResizeLeftAction;
import de.jumpnbump.usecases.viewer.Viewer.actions.ResizeRightAction;
import de.jumpnbump.usecases.viewer.Viewer.actions.ResizeTopMouseAction;
import de.jumpnbump.usecases.viewer.model.GameObject;
import de.jumpnbump.usecases.viewer.xml.ObjectContainer;

public class CanvasMouseListener implements MouseListener, MouseMotionListener {

	private final ObjectContainer container;
	private final MyCanvas canvas;
	private MouseAction nextAction;

	public CanvasMouseListener(ObjectContainer container, MyCanvas canvas) {
		super();
		this.container = container;
		this.canvas = canvas;
		this.nextAction = new MoveAction(this.canvas);
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
		this.nextAction = new MoveAction(this.canvas);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.nextAction.newMousePosition(e);
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
			this.nextAction = new MoveAction(this.canvas);
			if (Math.abs(e.getX() - translateToPixelX(pixelMinX)) < 5) {
				this.canvas.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
				this.nextAction = new ResizeLeftAction(selectedGameObject, this.canvas);
			}
			if (Math.abs(e.getX() - translateToPixelX(pixelMaxX)) < 5) {
				this.canvas.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
				this.nextAction = new ResizeRightAction(selectedGameObject, this.canvas);
			}
			if (Math.abs(e.getY() - translateToPixelY(pixelMinY)) < 5) {
				this.canvas.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
				this.nextAction = new ResizeDownAction(selectedGameObject, this.canvas);
			}
			if (Math.abs(e.getY() - translateToPixelY(pixelMaxY)) < 5) {
				this.canvas.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
				this.nextAction = new ResizeTopMouseAction(selectedGameObject, this.canvas);
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
