package de.oetting.bumpingbunnies.pc.graphics;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;

public class PcObjectsDrawer implements PlayerJoinListener, ObjectsDrawer {

	private List<Wall> walls;
	private CanvasCoordinatesTranslation canvasTranslation;

	public PcObjectsDrawer(List<Wall> walls, CanvasCoordinatesTranslation canvasTranslation) {
		super();
		this.walls = walls;
		this.canvasTranslation = canvasTranslation;
	}

	@Override
	public void newPlayerJoined(Player p) {

	}

	@Override
	public void playerLeftTheGame(Player p) {

	}

	public void draw(Canvas canvas) {
		for (Wall wall : walls) {
			canvasTranslation.drawRect(wall.minX(), wall.minY(), wall.maxX(), wall.maxY(), Color.BEIGE);
		}
	}

}
