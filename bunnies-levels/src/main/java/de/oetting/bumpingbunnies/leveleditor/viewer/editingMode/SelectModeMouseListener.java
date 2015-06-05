package de.oetting.bumpingbunnies.leveleditor.viewer.editingMode;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.leveleditor.viewer.ViewerPanel;
import de.oetting.bumpingbunnies.leveleditor.viewer.actions.CanvasObjectsFinder;
import de.oetting.bumpingbunnies.leveleditor.viewer.actions.MouseAction;
import de.oetting.bumpingbunnies.leveleditor.viewer.actions.MoveAction;
import de.oetting.bumpingbunnies.leveleditor.viewer.actions.ResizeDownAction;
import de.oetting.bumpingbunnies.leveleditor.viewer.actions.ResizeLeftAction;
import de.oetting.bumpingbunnies.leveleditor.viewer.actions.ResizeRightAction;
import de.oetting.bumpingbunnies.leveleditor.viewer.actions.ResizeTopMouseAction;
import de.oetting.bumpingbunnies.leveleditor.viewer.actions.SelectAction;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public class SelectModeMouseListener implements ModeMouseListener {

	private static final int TOLERANCE = 5;
	private MouseAction nextAction;
	private final CoordinatesCalculation coordinatesCalculation;
	private final SelectionModeProvider provider;
	private final ViewerPanel panel;

	public SelectModeMouseListener(SelectionModeProvider provider, ViewerPanel panel) {
		this.provider = provider;
		this.panel = panel;
		this.coordinatesCalculation = provider.createCoordinatesCalculation();
		resetAction();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1)
			this.nextAction.rightMouseClick(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			this.nextAction.onMousePressedFirst(e);
		else
			this.nextAction.rightMouseClick(e);
	}

	public void mouseReleased(MouseEvent e) {
		resetAction();
		provider.refreshView();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.nextAction.onMouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		List<GameObjectWithImage> objects = provider.getCurrentSelectedObjects();
		boolean modeWasFound = false;
		for (GameObjectWithImage selectedGameObject : objects) {
			int pixelMinX = coordinatesCalculation.getScreenCoordinateX(selectedGameObject.minX());
			int pixelMaxX = coordinatesCalculation.getScreenCoordinateX(selectedGameObject.maxX());
			int pixelMinY = coordinatesCalculation.getScreenCoordinateY(selectedGameObject.minY());
			int pixelMaxY = coordinatesCalculation.getScreenCoordinateY(selectedGameObject.maxY());
			if (isMouseOverSelectedObject(e, selectedGameObject)) {
				modeWasFound = true;
				if (Math.abs(e.getX() - pixelMinX) < TOLERANCE) {
					provider.setCanvasCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
					this.nextAction = new ResizeLeftAction(selectedGameObject, this.provider,
							this.coordinatesCalculation);
				} else if (Math.abs(e.getX() - pixelMaxX) < TOLERANCE) {
					this.provider.setCanvasCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
					this.nextAction = new ResizeRightAction(selectedGameObject, this.provider,
							this.coordinatesCalculation);
				} else if (Math.abs(e.getY() - pixelMinY) < TOLERANCE) {
					this.provider.setCanvasCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					this.nextAction = new ResizeDownAction(selectedGameObject, this.provider,
							this.coordinatesCalculation);
				} else if (Math.abs(e.getY() - pixelMaxY) < TOLERANCE) {
					this.provider.setCanvasCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					this.nextAction = new ResizeTopMouseAction(selectedGameObject, this.provider,
							this.coordinatesCalculation);
				} else {
					this.nextAction = new MoveAction(this.provider, this.coordinatesCalculation);
					provider.setCanvasCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				break;
			}
		}
		if (!modeWasFound)
			resetAction();
	}

	private boolean isMouseOverSelectedObject(MouseEvent e, GameObject selectedGameObject) {
		int pixelMinX = translateToPixelX(selectedGameObject.minX());
		int pixelMaxX = translateToPixelX(selectedGameObject.maxX());
		int pixelMinY = translateToPixelY(selectedGameObject.minY());
		int pixelMaxY = translateToPixelY(selectedGameObject.maxY());
		return e.getX() + TOLERANCE > pixelMinX && e.getX() - TOLERANCE < pixelMaxX && e.getY() - TOLERANCE < pixelMinY
				&& e.getY() + TOLERANCE > pixelMaxY;
	}

	private void resetAction() {
		provider.setCanvasCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.nextAction = new SelectAction(provider, new CanvasObjectsFinder(provider), coordinatesCalculation, panel);
	}

	private int translateToPixelX(long gameX) {
		return this.coordinatesCalculation.getScreenCoordinateX(gameX);
	}

	private int translateToPixelY(long gameY) {
		return this.coordinatesCalculation.getScreenCoordinateY(gameY);
	}

}
