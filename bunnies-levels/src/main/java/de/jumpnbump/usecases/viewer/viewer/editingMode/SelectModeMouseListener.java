package de.jumpnbump.usecases.viewer.viewer.editingMode;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.Optional;

import de.jumpnbump.usecases.viewer.viewer.actions.CanvasObjectsFinder;
import de.jumpnbump.usecases.viewer.viewer.actions.MouseAction;
import de.jumpnbump.usecases.viewer.viewer.actions.MoveAction;
import de.jumpnbump.usecases.viewer.viewer.actions.ResizeDownAction;
import de.jumpnbump.usecases.viewer.viewer.actions.ResizeLeftAction;
import de.jumpnbump.usecases.viewer.viewer.actions.ResizeRightAction;
import de.jumpnbump.usecases.viewer.viewer.actions.ResizeTopMouseAction;
import de.jumpnbump.usecases.viewer.viewer.actions.SelectAction;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;

public class SelectModeMouseListener implements ModeMouseListener {

	private static final int TOLERANCE = 5;
	private MouseAction nextAction;
	private final CoordinatesCalculation coordinatesCalculation;
	private final SelectionModeProvider provider;

	public SelectModeMouseListener(SelectionModeProvider provider) {
		this.provider = provider;
		this.coordinatesCalculation = provider.createCoordinatesCalculation();
		resetAction();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			this.nextAction.newMousePosition(e);
		else
			this.nextAction.rightMouseClick(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		resetAction();
		provider.refreshTables();
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
		Optional<? extends GameObject> object = provider.getCurrentSelectedObject();
		if (object.isPresent()) {
			GameObject selectedGameObject = object.get();
			int pixelMinX = coordinatesCalculation.getScreenCoordinateX(selectedGameObject.minX());
			int pixelMaxX = coordinatesCalculation.getScreenCoordinateX(selectedGameObject.maxX());
			int pixelMinY = coordinatesCalculation.getScreenCoordinateY(selectedGameObject.minY());
			int pixelMaxY = coordinatesCalculation.getScreenCoordinateY(selectedGameObject.maxY());
			if (isMouseOverSelectedObject(e, selectedGameObject)) {
				this.nextAction = new MoveAction(this.provider, this.coordinatesCalculation);
				if (Math.abs(e.getX() - pixelMinX) < TOLERANCE) {
					provider.setCanvasCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
					this.nextAction = new ResizeLeftAction(selectedGameObject, this.provider, this.coordinatesCalculation);
				}
				if (Math.abs(e.getX() - pixelMaxX) < TOLERANCE) {
					this.provider.setCanvasCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
					this.nextAction = new ResizeRightAction(selectedGameObject, this.provider, this.coordinatesCalculation);
				}
				if (Math.abs(e.getY() - pixelMinY) < TOLERANCE) {
					this.provider.setCanvasCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					this.nextAction = new ResizeDownAction(selectedGameObject, this.provider, this.coordinatesCalculation);
				}
				if (Math.abs(e.getY() - pixelMaxY) < TOLERANCE) {
					this.provider.setCanvasCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					this.nextAction = new ResizeTopMouseAction(selectedGameObject, this.provider, this.coordinatesCalculation);
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
		return e.getX() + TOLERANCE > pixelMinX && e.getX() - TOLERANCE < pixelMaxX && e.getY() - TOLERANCE < pixelMinY && e.getY() + TOLERANCE > pixelMaxY;
	}

	private void resetAction() {
		provider.setCanvasCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.nextAction = new SelectAction(this.coordinatesCalculation, provider, new CanvasObjectsFinder(provider));
	}

	private int translateToPixelX(long gameX) {
		return this.coordinatesCalculation.getScreenCoordinateX(gameX);
	}

	private int translateToPixelY(long gameY) {
		return this.coordinatesCalculation.getScreenCoordinateY(gameY);
	}

}
