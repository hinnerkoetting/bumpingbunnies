package de.jumpnbump.usecases.viewer.viewer.editingMode;

import java.awt.event.MouseEvent;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.FixedWorldObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public abstract class CreateObjectEditingMode<S extends FixedWorldObject> implements ModeMouseListener {

	private final SelectionModeProvider provider;
	private final CoordinatesCalculation coordinatesCalculation;
	private final EditorObjectFactory<S> objectFactory;
	private final MyCanvas canvas;

	private int startX;
	private int startY;

	public CreateObjectEditingMode(SelectionModeProvider provider, EditorObjectFactory<S> objectFactory, MyCanvas canvas) {
		this.provider = provider;
		this.objectFactory = objectFactory;
		this.canvas = canvas;
		coordinatesCalculation = provider.createCoordinatesCalculation();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		startX = arg0.getX();
		startY = arg0.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		canvas.setCurrentlyEditedObject(null);
		if (objectHasMinSize(arg0))
			createWall(arg0);
	}

	private void createWall(MouseEvent arg0) {
		S newWall = createObject(arg0);
		addToWorld(newWall);
		provider.refreshAll();
	}

	private S createObject(MouseEvent arg0) {
		long gameX = coordinatesCalculation.getGameCoordinateX(startX);
		long gameY = coordinatesCalculation.getGameCoordinateY(startY);
		long gameEndX = coordinatesCalculation.getGameCoordinateX(arg0.getX());
		long gameEndY = coordinatesCalculation.getGameCoordinateY(arg0.getY());
		S newObject = create(gameX, gameY, gameEndX, gameEndY);
		newObject.setzIndex(provider.getMaxZIndexValue() + 1);
		return newObject;
	}

	protected abstract void addToWorld(S newWall);

	private S create(long gameX, long gameY, long gameEndX, long gameEndY) {
		return objectFactory.create(Math.min(gameX, gameEndX), Math.min(gameY, gameEndY), Math.max(gameX, gameEndX),
				Math.max(gameY, gameEndY));
	}

	private boolean objectHasMinSize(MouseEvent arg0) {
		int endX = arg0.getX();
		int endY = arg0.getY();
		return Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2) > minSize();
	}

	private int minSize() {
		return 100;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		S newWall = createObject(arg0);
		canvas.setCurrentlyEditedObject(newWall);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	protected SelectionModeProvider getProvider() {
		return provider;
	}
}
