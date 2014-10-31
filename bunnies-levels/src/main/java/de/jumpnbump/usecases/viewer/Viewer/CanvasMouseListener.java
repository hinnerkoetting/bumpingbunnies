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
import de.oetting.bumpingbunnies.core.game.graphics.calculation.AbsoluteCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;

public class CanvasMouseListener implements MouseListener, MouseMotionListener {

	private static final int TOLERANCE = 5;
	private final World container;
	private final MyCanvas canvas;
	private final ViewerPanel viewerPanel;
	private MouseAction nextAction;
	private final CoordinatesCalculation coordinatesCalculation;

	public CanvasMouseListener(World container, MyCanvas canvas, ViewerPanel viewerPanel) {
		super();
		this.container = container;
		this.canvas = canvas;
		this.viewerPanel = viewerPanel;
		WorldProperties properties = new WorldProperties();
		this.coordinatesCalculation = new AbsoluteCoordinatesCalculation(canvas.getWidth(), canvas.getHeight(), properties);
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

			int pixelMinX = coordinatesCalculation.getScreenCoordinateX(selectedGameObject.minX());
			int pixelMaxX = coordinatesCalculation.getScreenCoordinateX(selectedGameObject.maxX());
			int pixelMinY = coordinatesCalculation.getScreenCoordinateY(selectedGameObject.minY());
			int pixelMaxY = coordinatesCalculation.getScreenCoordinateY(selectedGameObject.maxY());
			if (isMouseOverSelectedObject(e, selectedGameObject)) {
				this.nextAction = new MoveAction(this.canvas, this.coordinatesCalculation);
				if (Math.abs(e.getX() - pixelMinX) < TOLERANCE) {
					this.canvas.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
					this.nextAction = new ResizeLeftAction(selectedGameObject, this.canvas, this.coordinatesCalculation);
				}
				if (Math.abs(e.getX() - pixelMaxX) < TOLERANCE) {
					this.canvas.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
					this.nextAction = new ResizeRightAction(selectedGameObject, this.canvas, this.coordinatesCalculation);
				}
				if (Math.abs(e.getY() - pixelMinY) < TOLERANCE) {
					this.canvas.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					this.nextAction = new ResizeDownAction(selectedGameObject, this.canvas, this.coordinatesCalculation);
				}
				if (Math.abs(e.getY() - pixelMaxY) < TOLERANCE) {
					this.canvas.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					this.nextAction = new ResizeTopMouseAction(selectedGameObject, this.canvas, this.coordinatesCalculation);
				}
			} else {
				resetAction();
			}

		}
	}

	private boolean isMouseOverSelectedObject(MouseEvent e, GameObject selectedGameObject) {
		int pixelMinX = translateToPixelX(selectedGameObject.minX());
		int pixelMaxX = translateToPixelX(selectedGameObject.maxX());
		int pixelMinY = translateToPixelY(selectedGameObject.minY());
		int pixelMaxY = translateToPixelY(selectedGameObject.maxY());
		return (e.getX() + TOLERANCE > pixelMinX && e.getX() - TOLERANCE < pixelMaxX && e.getY() - TOLERANCE < pixelMinY && e.getY() + TOLERANCE > pixelMaxY);
	}

	private void resetAction() {
		this.canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.nextAction = new SelectAction(this.canvas, this.container, this.coordinatesCalculation);
	}

	private int translateToPixelX(long gameX) {
		return this.coordinatesCalculation.getScreenCoordinateX(gameX);
	}

	private int translateToPixelY(long gameY) {
		return (this.coordinatesCalculation.getScreenCoordinateY(gameY));
	}

}
