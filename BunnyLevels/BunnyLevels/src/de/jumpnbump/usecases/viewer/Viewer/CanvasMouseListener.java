package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.Viewer.actions.MouseAction;
import de.jumpnbump.usecases.viewer.Viewer.actions.MoveAction;
import de.jumpnbump.usecases.viewer.Viewer.actions.ResizeDownAction;
import de.jumpnbump.usecases.viewer.Viewer.actions.ResizeLeftAction;
import de.jumpnbump.usecases.viewer.Viewer.actions.ResizeRightAction;
import de.jumpnbump.usecases.viewer.Viewer.actions.ResizeTopMouseAction;
import de.jumpnbump.usecases.viewer.Viewer.actions.SelectAction;
import de.jumpnbump.usecases.viewer.model.GameObject;
import de.jumpnbump.usecases.viewer.xml.ObjectContainer;

public class CanvasMouseListener implements MouseListener, MouseMotionListener {

	private static final int TOLERANCE = 5;
	private final ObjectContainer container;
	private final MyCanvas canvas;
	private final ViewerPanel viewerPanel;
	private MouseAction nextAction;

	public CanvasMouseListener(ObjectContainer container, MyCanvas canvas, ViewerPanel viewerPanel) {
		super();
		this.container = container;
		this.canvas = canvas;
		this.viewerPanel = viewerPanel;
		resetAction();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.nextAction.newMousePosition(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		resetAction();
		this.viewerPanel.refreshTables();
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

	@Override
	public void mouseMoved(MouseEvent e) {
		GameObject selectedGameObject = this.canvas.getSelectedGameObject();
		if (selectedGameObject != null) {

			int pixelMinX = selectedGameObject.minX();
			int pixelMaxX = selectedGameObject.maxX();
			int pixelMinY = selectedGameObject.minY();
			int pixelMaxY = selectedGameObject.maxY();
			if (isMouseOverSelectedObject(e, selectedGameObject)) {
				this.nextAction = new MoveAction(this.canvas);
				if (Math.abs(e.getX() - translateToPixelX(pixelMinX)) < TOLERANCE) {
					this.canvas.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
					this.nextAction = new ResizeLeftAction(selectedGameObject, this.canvas);
				}
				if (Math.abs(e.getX() - translateToPixelX(pixelMaxX)) < TOLERANCE) {
					this.canvas.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
					this.nextAction = new ResizeRightAction(selectedGameObject, this.canvas);
				}
				if (Math.abs(e.getY() - translateToPixelY(pixelMinY)) < TOLERANCE) {
					this.canvas.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					this.nextAction = new ResizeDownAction(selectedGameObject, this.canvas);
				}
				if (Math.abs(e.getY() - translateToPixelY(pixelMaxY)) < TOLERANCE) {
					this.canvas.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					this.nextAction = new ResizeTopMouseAction(selectedGameObject, this.canvas);
				}
			} else {
				resetAction();
			}

		}
	}

	private boolean isMouseOverSelectedObject(MouseEvent e, GameObject selectedGameObject) {
		int pixelMinX = selectedGameObject.minX();
		int pixelMaxX = selectedGameObject.maxX();
		int pixelMinY = selectedGameObject.minY();
		int pixelMaxY = selectedGameObject.maxY();
		return (e.getX() + TOLERANCE > translateToPixelX(pixelMinX) && e.getX() - TOLERANCE < translateToPixelX(pixelMaxX)
				&& e.getY() - TOLERANCE < translateToPixelY(pixelMinY) && e.getY() + TOLERANCE > translateToPixelY(pixelMaxY));
	}

	private void resetAction() {
		this.canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.nextAction = new SelectAction(this.canvas, this.container);
	}

	private int translateToPixelX(int gameX) {
		return CoordinatesCalculation.calculatePixelX(gameX / this.canvas.getZoom());
	}

	private int translateToPixelY(int gameY) {
		return (int) (CoordinatesCalculation.calculatePixelY(gameY, (this.canvas.getHeight())) / this.canvas.getZoom());
	}

}
