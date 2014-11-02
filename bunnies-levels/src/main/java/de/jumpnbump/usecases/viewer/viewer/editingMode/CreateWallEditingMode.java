package de.jumpnbump.usecases.viewer.viewer.editingMode;

import java.awt.event.MouseEvent;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.ObjectsFactory;
import de.oetting.bumpingbunnies.model.game.objects.Wall;

public class CreateWallEditingMode implements ModeMouseListener {

	private final SelectionModeProvider provider;
	private final CoordinatesCalculation coordinatesCalculation;

	private int startX;
	private int startY;

	public CreateWallEditingMode(SelectionModeProvider provider) {
		this.provider = provider;
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
		if (objectHasMinSize(arg0))
			createWall(arg0);
	}

	private void createWall(MouseEvent arg0) {
		long gameX = coordinatesCalculation.getGameCoordinateX(startX);
		long gameY = coordinatesCalculation.getGameCoordinateY(startY);
		long gameEndX = coordinatesCalculation.getGameCoordinateX(arg0.getX());
		long gameEndY = coordinatesCalculation.getGameCoordinateY(arg0.getY());
		Wall newWall = create(gameX, gameY, gameEndX, gameEndY);
		World world = provider.getWorld();
		world.addWall(newWall);
		provider.refreshAll();
	}

	private Wall create(long gameX, long gameY, long gameEndX, long gameEndY) {
		return ObjectsFactory.createWall(Math.min(gameX, gameEndX), Math.min(gameY, gameEndY), Math.max(gameX, gameEndX), Math.max(gameY, gameEndY));
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
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

}
